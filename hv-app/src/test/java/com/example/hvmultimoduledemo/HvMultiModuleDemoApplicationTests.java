package com.example.hvmultimoduledemo;

import org.assertj.core.api.Assertions;
import org.hibernate.validator.constraints.Length;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HvMultiModuleDemoApplicationTests {

  @Autowired
  Validator bv;

  @Test
  public void noViolation() {
    Bean bean = new Bean();
    bean.target = "aa";
    Set<ConstraintViolation<Bean>> violations = bv.validate(bean);
    Assertions.assertThat(violations).hasSize(0);
  }

  @Test
  public void existsViolation() {
    Bean bean = new Bean();
    bean.target = "a";
    Set<ConstraintViolation<Bean>> violations = bv.validate(bean);
    Assertions.assertThat(violations).hasSize(2);
    List<String> messages = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
    Assertions.assertThat(messages).contains(
        "'Length' must be between 2 and 4"
        , "'Size' must be between 2 and 4"
    );
  }

  private static class Bean {
    @Size(min = 2, max = 4)
    @Length(min = 2, max = 4)
    private String target;
  }

}
