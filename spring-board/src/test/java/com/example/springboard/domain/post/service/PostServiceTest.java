package com.example.springboard.domain.post.service;

import com.example.springboard.domain.post.controller.PostController;
import com.example.springboard.domain.post.repository.PostRedisDao;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    private static final int THREAD_COUNT = 20;
    private static final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);

    @Autowired
    private PostController postController;
    @Autowired
    private PostRedisDao postRedisDao;

    private MockMvc createMockMvc() {
        return MockMvcBuilders.standaloneSetup(postController).build();
    }

    @Test
    void 조회수_동기화_테스트() throws Exception {
        int beforeViews = postRedisDao.getViews(1L);

        int count = 30;
        for(int i = 0; i < THREAD_COUNT; i++) {
            executorService.submit(() ->
                    createRequestThread(count).run()
            );
        }

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);

        int afterViews = postRedisDao.getViews(1L);

        Assertions.assertThat(afterViews).isEqualTo(beforeViews + THREAD_COUNT * count);

    }

    private Runnable createRequestThread(int count) {
        Runnable runnable = () -> {
            MockMvc mockMvc = createMockMvc();
            for (int j = 0; j < count; j++) {
                try {
                    mockMvc.perform(MockMvcRequestBuilders.get("/posts/1"))
                            .andExpect(MockMvcResultMatchers.status().isOk());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
        return runnable;
    }
}