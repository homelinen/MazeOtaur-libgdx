package uk.calumgilchrist.mazeotaur.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestPlayer.class, TestMaze.class })
public class MazeSuite {

	@Test
	public void test() {}

}