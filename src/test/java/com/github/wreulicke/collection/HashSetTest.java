package com.github.wreulicke.collection;

import static org.assertj.core.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import lombok.AllArgsConstructor;

/**
 * @author Wreulicke
 *
 */
public class HashSetTest {
  /**
   * Setに存在しない値をaddした場合、追加された旨のtrueが返り値として返却される
   */
  @Test
  public void addIfAbsent() {
    Set<String> set = new HashSet<>();
    assertThat(set.add("a")).isTrue();
  }

  /**
   * Setに存在する値をaddした場合、追加されていない旨のfalseが返り値として返却される
   */
  @Test
  public void addIfPresent() {
    Set<String> set = new HashSet<>();
    set.add("test");
    assertThat(set.add("test")).isFalse();
  }

  /**
   * HashSetではhashを元に内部のTable上のどこに値の配置されるかどうかが決定される。
   * その時、hashが衝突した場合はequalityによって追加されるかどうかが決定される。
   * この例の場合、等価なので、追加されずaddメソッドの戻り値としてはfalseになる。
   */
  @SuppressWarnings({
    "rawtypes",
    "unchecked"
  })
  @Test
  public void addIfHashCodeConflicts() {
    // 本来raw typeは避けるべきである。
    Set set = new HashSet<>();
    assertThat(set.add(new Hash(2))).isTrue();
    assertThat(set.add(new Dummy(2))).isFalse();
    assertThat(set).hasSize(1);

  }

  /**
   * hashは衝突するが、equalityはequalsを実装していないため、参照比較になる。
   * そのため、以下の例ではSetに二つ共追加できる。
   */
  @Test
  public void addIfHashCodeConflicts2() {
    Set<Hash> set = new HashSet<Hash>();
    assertThat(set.add(new Hash(2))).isTrue();
    assertThat(set.add(new Hash(2))).isTrue();
    assertThat(set).hasSize(2);

  }

  /**
   * HashSetはnull許容のSetである。
   * nullを許容するかどうかはSetの実装によって変化する
   */
  @Test
  public void addNull() {
    Set<String> set = new HashSet<>();
    assertThat(set.add(null)).isTrue();
  }

  /**
   * HashSetのテストのための実装です。
   * 必ず真似しないでください。
   *
   * @author Wreulicke
   *
   */
  @AllArgsConstructor
  public static class Hash {
    private final int hash;

    /**
     * この実装ではhashCodeを実装していますが
     * 正しい実装ではありません。
     * hashCodeをオーバーライドする場合はequalsメソッドのオーバーライドしてください。
     * 詳細はJava SEの{@link Object}クラスのJavadocを確認してください。
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public int hashCode() {
      return hash;
    }
  }

  /**
   * HashSetのテストのための実装です。
   * 必ず真似しないでください。
   *
   * @author Wreulicke
   *
   */
  @AllArgsConstructor
  public static class Dummy {
    private final int hash;

    /**
     * この実装ではhashCodeを実装していますが
     * 正しい実装ではありません。
     * hashCodeをオーバーライドする場合はequalsメソッドのオーバーライドしてください。
     * 詳細はJava SEの{@link Object}クラスのJavadocを確認してください。
     *
     * @see java.lang.Object#equals(java.lang.Object)
     * @see java.lang.Object#hashCode()
     */
    @Deprecated
    @Override
    public int hashCode() {
      return hash;
    }

    /**
     * HashSetのテストのための実装です。
     * 必ず真似しないでください。
     */
    @Override
    public boolean equals(Object obj) {
      return obj != null;
    }
  }

}
