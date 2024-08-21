package com.concord.petmily.domain.post.service;

import com.concord.petmily.common.exception.ErrorCode;
import com.concord.petmily.common.utils.FileUtils;
import com.concord.petmily.domain.post.dto.PostDto;
import com.concord.petmily.domain.post.entity.*;
import com.concord.petmily.domain.post.exception.PostException;
import com.concord.petmily.domain.post.repository.*;
import com.concord.petmily.domain.user.entity.User;
import com.concord.petmily.domain.user.exception.UserException;
import com.concord.petmily.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 게시물 서비스
 * 비즈니스 로직을 처리
 */
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostCategoryRepository postCategoryRepository;
    private final PostViewRepository postViewRepository;
    private final HashtagRepository hashtagRepository;
    private final PostHashtagRepository postHashtagRepository;

    // TODO file.dir 위치 수정
    @Value("${file.dir}")
    private String fileDir;

    @Override
    @Transactional
    public PostDto.Response createPost(String username, PostDto.Request dto, List<MultipartFile> files) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));

        PostCategory postCategory = postCategoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new PostException(ErrorCode.POST_CATEGORY_NOT_FOUND));

        Post post = postRepository.save(dto.toEntity());
        post.setPostCategory(postCategory);
        post.setUser(user);

        // 파일이 있다면 저장
        if (files != null && !files.isEmpty()) {
            saveImage(user.getId(), post, files);
        }

        PostDto.Response response = saveHashtags(post, dto);
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostDto.ResponseGetPosts> getPosts(Long categoryId, String hashtagName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        if (categoryId == null && hashtagName == null) {
            // categoryId, hashtagName 모두 입력하지 않은 경우
            return postRepository.findAll(pageable).map(PostDto.ResponseGetPosts::new);

        } else if (categoryId == null) {
            // categoryId 입력하지 않은 경우
            Set<Long> postIds = searchPostIds(hashtagName);
            return postRepository.findByIdIn(postIds, pageable).map(PostDto.ResponseGetPosts::new);

        } else if (hashtagName == null) {
            // hashtagName 입력하지 않은 경우
            return postRepository.findByPostCategoryId(categoryId, pageable).map(PostDto.ResponseGetPosts::new);

        } else {
            // categoryId, hashtagName 모두 입력한 경우
            Set<Long> postIds = searchPostIds(hashtagName);
            return postRepository.findByPostCategoryIdAndIdIn(categoryId, postIds, pageable).map(PostDto.ResponseGetPosts::new);
        }
    }

    @Override
    @Transactional
    public PostDto.Response getPost(String username, Long postId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorCode.POST_NOT_FOUND));

        Optional<PostView> existingRecord = postViewRepository.findByUserIdAndPostId(user.getId(), postId);
        // 조회한 기록이 없는 경우
        if (existingRecord.isEmpty()) {
            // 새 조회 기록 저장 후 조회수 + 1
            PostView newRecord = new PostView();
            newRecord.setUser(user);
            newRecord.setPost(post);
            newRecord.setViewTimestamp(System.currentTimeMillis());
            postViewRepository.save(newRecord);
            viewCountUp(postId);
        }

        PostDto.Response response = new PostDto.Response(post);

        // 해시태그 조회
        Set<PostHashtag> postHashtags = postHashtagRepository.findAllByPostId(post.getId());
        Set<Long> hashtagIds = postHashtags.stream()
                .map(postHashtag -> postHashtag.getHashtag().getId())
                .collect(Collectors.toSet());
        Set<String> hashtagNames = hashtagIds.stream()
                .map(id -> hashtagRepository.findById(id)
                        .map(Hashtag::getHashtagName)
                        .orElse(null))
                .collect(Collectors.toSet());
        response.setHashtagNames(hashtagNames);

        return response;
    }

    @Override
    @Transactional
    public PostDto.Response updatePost(String username, Long postId, PostDto.Request dto, List<MultipartFile> files) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorCode.POST_NOT_FOUND));

        PostCategory postCategory = postCategoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new PostException(ErrorCode.POST_CATEGORY_NOT_FOUND));

        // 이미 삭제된 게시물이라면 에러
        if(post.getPostStatus() == PostStatus.DELETED) {
            throw new PostException(ErrorCode.POST_ALREADY_DELETED);
        }

        // 사용자와 게시물 작성자가 다른 경우 에러
        if(!Objects.equals(user.getId(), post.getUser().getId())) {
            throw new PostException(ErrorCode.USER_POST_UNMATCHED);
        }

        post.setPostCategory(postCategory);
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setPostStatus(dto.getPostStatus());

        // 게시물 수정시 썸네일, 이미지파일 업데이트
        if (files != null && !files.isEmpty()) {
            saveImage(user.getId(), post, files);
        }

        // 게시물 수정시 post_hashtag 테이블의 post_id 컬럼을 null로 변경
        Set<PostHashtag> postHashtags = postHashtagRepository.findAllByPostId(post.getId());
        for (PostHashtag postHashtag : postHashtags) {
            postHashtag.setPost(null);
        }
        postHashtagRepository.saveAll(postHashtags);

        PostDto.Response response = saveHashtags(post, dto);
        return response;
    }

    @Override
    @Transactional
    public void deletePost(String username, Long postId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorCode.POST_NOT_FOUND));

        // 이미 삭제된 게시물이라면 에러
        if(post.getPostStatus() == PostStatus.DELETED) {
            throw new PostException(ErrorCode.POST_ALREADY_DELETED);
        }

        // 사용자와 게시물 작성자가 다른 경우 에러
        if(!Objects.equals(user.getId(), post.getUser().getId())) {
            throw new PostException(ErrorCode.USER_POST_UNMATCHED);
        }

        // 게시물 삭제시 post 테이블의 status 변경
        post.setPostStatus(PostStatus.DELETED);

        // 게시물 삭제시 post_hashtag 테이블의 post_id 컬럼을 null로 변경
        Set<PostHashtag> postHashtags = postHashtagRepository.findAllByPostId(post.getId());
        for (PostHashtag postHashtag : postHashtags) {
            postHashtag.setPost(null);
        }
        postHashtagRepository.saveAll(postHashtags);
    }

    @Override
    @Transactional
    public Set<String> getAllHashtags() {
        List<Hashtag> hashtags = hashtagRepository.findAll();
        if(hashtags.isEmpty()) {
            throw new PostException(ErrorCode.HASHTAG_NOT_FOUND);
        }

        return hashtags.stream().map(Hashtag::getHashtagName).collect(Collectors.toSet());
    }

    // 게시물 조회수 증가
    public void viewCountUp(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorCode.POST_NOT_FOUND));
        post.viewCountUp(post);
    }

    // 새로운 hashtag 등록
    public PostDto.Response saveHashtags(Post post, PostDto.Request dto) {
        for(String hashtagName : dto.getHashtagNames()) {
            Hashtag hashtag = hashtagRepository.findByHashtagName(hashtagName)
                    .orElseGet(() -> hashtagRepository.save(new Hashtag(hashtagName)));
            postHashtagRepository.save(new PostHashtag(post, hashtag));
        }
        postRepository.save(post);

        PostDto.Response response = new PostDto.Response(post);
        response.setHashtagNames(dto.getHashtagNames());

        return response;
    }

    // hashtagName으로 PostId 찾기
    public Set<Long> searchPostIds(String hashtagName) {
        Hashtag hashtag = hashtagRepository.findByHashtagName(hashtagName)
                .orElseThrow(() -> new PostException(ErrorCode.HASHTAG_NOT_FOUND));
        Set<PostHashtag> postHashtags = postHashtagRepository.findAllByHashtagId(hashtag.getId());
        Set<Long> postIds = postHashtags.stream()
                .map(postHashtag -> postHashtag.getPost().getId())
                .collect(Collectors.toSet());

        if (postIds.isEmpty()) {
            // 해당 해시태그로 관련된 게시물이 없는 경우
            throw new PostException(ErrorCode.POST_NOT_FOUND);
        }
        return postIds;
    }

    public String saveImage(Long userId, Post post, List<MultipartFile> files) {
        // 파일 저장 경로
        String savedFullPath = String.format("%s/post/%d/%d",
                FileUtils.getAbsolutePath(fileDir), userId, post.getId());

        // 디렉토리가 존재하지 않으면 생성
        File uploadDir = new File(savedFullPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String thumbnailPath = null;
        List<String> imagePaths = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            String originalFileName = file.getOriginalFilename();
            String randomFileName = FileUtils.makeRandomFileNm(originalFileName);
            File destinationFile = new File(uploadDir, randomFileName);

            // 파일 실제 경로로 저장
            try {
                file.transferTo(destinationFile);
            } catch (IOException e) {
                throw new PostException(ErrorCode.FILE_SAVE_FAILED);
            }

            String imagePath = savedFullPath + randomFileName;
            imagePaths.add(imagePath);

            // 첫번째 파일이라면, 썸네일 이미지로 사용
            if (i == 0) {
                thumbnailPath = savedFullPath + randomFileName;
                post.setThumbnailPath(thumbnailPath);
            }
        }
        post.setImagePaths(imagePaths);
        return thumbnailPath;
    }
}