package persistence.sql.ddl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import domain.Person;
import persistence.sql.ddl.test.NoExistEntity;

class DdlQueryBuilderTest {

    @DisplayName("@Entity 어노테이션이 존재하면 인스턴스를 생성해야 한다")
    @Test
    void createInstance() {
        // given
        Class<Person> entityClass = Person.class;

        // when
        DdlQueryBuilder builder = new DdlQueryBuilder(entityClass);

        // then
        Assertions.assertThat(builder)
            .isNotNull()
            .isInstanceOf(DdlQueryBuilder.class);
    }

    @DisplayName("@Entity 어노테이션이 존재하지 않으면 에러가 발생한다")
    @Test
    void createInstance_noExistEntity() {
        // given
        Class<NoExistEntity> entityClass = NoExistEntity.class;

        // when & then
        assertThatThrownBy(() -> new DdlQueryBuilder(entityClass))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("@Entity 어노테이션이 존재하지 않습니다");
    }

    @DisplayName("Create Query를 반환해야 한다")
    @Test
    void buildCreateQuery() {
        // given
        Class<Person> entityClass = Person.class;
        DdlQueryBuilder builder = new DdlQueryBuilder(entityClass);

        // when
        String query = builder.buildCreateTable();

        // then
        Assertions.assertThat(query)
            .isEqualTo("CREATE TABLE users (id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, nick_name VARCHAR(255) NOT NULL, old INTEGER NOT NULL, email VARCHAR(255))");
    }

    @DisplayName("Drop Query를 반환해야 한다")
    @Test
    void buildDropQuery() {
        // given
        Class<Person> entityClass = Person.class;
        DdlQueryBuilder builder = new DdlQueryBuilder(entityClass);

        // when
        String query = builder.buildDropTable();

        // then
        Assertions.assertThat(query)
            .isEqualTo("DROP TABLE IF EXISTS users");
    }

}