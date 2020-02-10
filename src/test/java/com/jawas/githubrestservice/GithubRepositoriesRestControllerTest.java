package com.jawas.githubrestservice;

import com.jawas.githubrestservice.rest.GithubRepositoriesRestController;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static org.hamcrest.core.Is.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Slf4j
@RunWith(SpringRunner.class)
@TestPropertySource
@WebMvcTest(GithubRepositoriesRestController.class)
@Import({com.jawas.githubrestservice.apiclient.github.GithubApiClientImpl.class})
public class GithubRepositoriesRestControllerTest
{
	@Autowired
	private MockMvc mvc;

	@Test
	public void testMultipleCallsToRestApi_ExpectSuccess() throws Exception
	{
		final List<CompletableFuture<Void>> futures = new ArrayList<>();
		final int requestsNumber = 2;

		for (int i = 0; i < requestsNumber; i++)
		{
			var future = CompletableFuture.runAsync(() ->
			{
				try
				{
					mvc.perform(get("/repositories/adrnkaczmarek/paxos"))
							.andExpect(status().isOk())
							.andExpect(jsonPath("$.fullName", is("adrnkaczmarek/paxos")))
							.andExpect(jsonPath("$.cloneUrl", is("https://github.com/adrnkaczmarek/paxos.git")));
				}
				catch (Exception e)
				{
					throw new CompletionException(e);
				}
			});

			futures.add(future);
		}

		CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
		combinedFuture.get();
	}

	@Test
	public void testCallToApi_ExpectNotFound() throws Exception
	{
		mvc.perform(get("/repositories/adrnkaczmarek/paxos1"))
				.andExpect(status().isNotFound());
	}
}
