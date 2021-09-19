package com.example.blog

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
class RepositoriesTests @Autowired constructor(
    val entityManager: TestEntityManager,
    val userRepository: UserRepository,
    val articleRepository: ArticleRepository,
) {

    @Test
    fun `When findByIdOrNull then return Article`() {
        val juergen = User("springjuergen", "Juergen", "Hoeller")
        entityManager.persist(juergen)
        val article = Article("Spring Framework 5.0 goes GA", "Dear Spring community ...", "Lorem ipsum", juergen)
        entityManager.persist(article)
        entityManager.flush()
        val found = articleRepository.findByIdOrNull(article.id!!)
        assertThat(found?.title.toString().equals(article.title).toString(),false)
    }

    @Test
    fun `When findByLogin then return User`() {
        val juergen = User("springjuergen", "Juergen", "Hoeller")
        val user = userRepository.findByLogin(juergen.login)
        assertThat(user?.firstname.toString().equals(juergen.firstname.toString()).toString(), false)
    }
}