package com.github.wreulicke.collection;


import static org.assertj.core.api.Assertions.*;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.Test;

/**
 * @author Wreulicke
 *
 */
public class OptionalTest {
  /**
   * flatMapのケース1
   * 値がない場合はflatMapの処理は実行されない
   */
  @Test
  public void flatmapWithNone() {
    assertThat(Optional.empty()
      .flatMap((opt) -> {
        fail("cannot reach here");
        return null;
      })).isEmpty();

  }

  /**
   * flatMapのケース2
   * 値がある場合はflatMapの処理は実行される
   *
   * 実行例：{@link OptionalTest}のクラスに付与されている{@link Test}アノテーションを取得するが
   * アノテーションを取得するAPI的にはNullの可能性があるのでofNullableを利用してflatMapの戻り値とする。
   */
  @Test
  public void flatmapWithSome() {
    Optional<Test> annotationOpt = Optional.of(OptionalTest.class)
      .flatMap((clazz) -> Optional.ofNullable(clazz.getAnnotation(Test.class)));
    assertThat(annotationOpt).isEmpty();
  }

  /**
   * flatMapのケース3
   * 値があるOptionalでflatMapにて値がない場合に変換した場合
   * 値がないOptionalに変化する
   */
  @Test
  public void convertNone() {
    assertThat(Optional.of("some")
      .flatMap((string) -> Optional.empty())).isEmpty();
  }

  /**
   * getのケース1
   * 値がある場合はgetでその値が取得できる
   */
  @Test
  public void getWithSome() {
    assertThat(Optional.of("test")
      .get()).isEqualTo("test");
  }

  /**
   * getのケース2
   * 値がない場合はgetで例外がthrowされる
   */
  @Test
  public void getWithNone() {
    assertThatThrownBy(Optional.empty()::get).hasMessage("No value present")
      .isInstanceOf(NoSuchElementException.class);
  }
}
