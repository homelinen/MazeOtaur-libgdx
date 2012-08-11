package uk.calumgilchrist.mazeotaur.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestPlayer.class, TestMazeTemplate.class, TestMazeGenerator.class, 
	TestAIManager.class, EnemyTest.class, VecterTest.class })
public class MazeSuite {

	@Test
	public void test() {}

}
