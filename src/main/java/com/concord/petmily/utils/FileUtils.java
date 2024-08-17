package com.concord.petmily.utils;

import java.io.File;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.UUID;

public class FileUtils {
  // 파일의 확장자 리턴하는 메소드
  public static String getExt(String fileNm) {
    int dotIdx = fileNm.lastIndexOf(".");
    String ext = fileNm.substring(dotIdx);
    return ext;
  }

  public static String getFile (String fileNm) {
    int dotIdx = fileNm.lastIndexOf(".");
    String ext = fileNm.substring(0, dotIdx);
    return ext;
  }
  public static String makeRandomFileNm (String fileNm) {
    String uuid = UUID.randomUUID().toString();
    String saveNm = uuid + getExt(fileNm);
    return saveNm;
  }

  public static String getAbsoluteDownloadPath(String path) {
    File file = new File(path);
    return file.getAbsolutePath();
  }

  //절대경로 리턴  이프로젝트를 실행하는 드라이브 리턴
  public static String getAbsolutePath(String src){
    return Paths.get(src).toFile().getAbsolutePath();
  }
}
