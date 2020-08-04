package banane.io.pdb.service

import banane.io.pdb.model.Hero
import banane.io.pdb.model.MapPoint
import banane.io.pdb.model.Terrain
import banane.io.pdb.repository.HeroRepository
import banane.io.pdb.repository.MapPointRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class MapPointServiceImplTest {

    lateinit var serviceToTest: MapPointServiceImpl
    lateinit var heroRepository: HeroRepository
    lateinit var mapPointRepository: MapPointRepository

    @BeforeEach
    fun setup() {
        heroRepository = mockk()
        mapPointRepository = mockk()

        serviceToTest = MapPointServiceImpl(mapPointRepository, heroRepository)
    }

    private fun createMapPointWith(terrain: Terrain): MapPoint {
        return MapPoint(1, 1, 1, "This is a description", terrain)
    }

    @Test
    fun `Verify that move Player should return the new MapPoint`() {
        val captureData = slot<Hero>()
        every { heroRepository.save(capture(captureData))} answers { captureData.captured }
        val mountainPoint = createMapPointWith(Terrain.MOUNTAIN)
        val hero = Hero()
        hero.currentZone = mountainPoint

        val forestPoint = createMapPointWith(Terrain.FOREST)

        val returnedHero = serviceToTest.moveHero(hero, forestPoint)
        assertEquals(Terrain.FOREST, returnedHero.currentZone?.terrain, "Terrain should be the one passed in parameter")
    }

}