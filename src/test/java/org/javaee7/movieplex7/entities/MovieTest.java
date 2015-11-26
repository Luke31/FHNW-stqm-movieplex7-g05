package org.javaee7.movieplex7.entities;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class MovieTest {
	@Before
	public void setUp(){
		
	}
	
	@Test
	public void testMovieEmpty(){
		Movie m = new Movie();
		assertEquals(null, m.getId());
		assertEquals(null, m.getName());
		assertEquals(null, m.getActors());
	}
	
	@Test
	public void testMovieFilled(){
		Movie m = new Movie(1);
		assertEquals((Integer)1, m.getId());
		assertEquals(null, m.getName());
		assertEquals(null, m.getActors());
	}
	
	@Test
	public void testMovieFilledFull(){
		Movie m = new Movie(2, "Movie 1", "Actor 1");
		assertEquals((Integer)2, m.getId());
		assertEquals("Movie 1", m.getName());
		assertEquals("Actor 1", m.getActors());
	}
	
	@Test
	public void testMovieHashCode(){
		Movie m = new Movie(2, "Movie 1", "Actor 1");
		assertEquals(2, m.hashCode());
	}
}
