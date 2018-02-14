package gol;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class WorldTest {

    World world;

    @Before
    public void setup() {
        world = new World();
    }

    @Test
    public void new_world_should_be_empty() {
        assertTrue(world.isEmpty());
    }

    @Test
    public void one_cell_can_be_added_to_world() {
        world.reviveAt(1, 1);
        assertThat(world.isAliveAt(1, 1), is(true));
        assertThat(world.isAliveAt(2, 2), is(false));
        assertThat(world.isEmpty(), is(false));
    }

    @Test
    public void several_cells_can_be_added() {
        world.reviveAt(1, 1);
        world.reviveAt(2, 1);
        world.reviveAt(1, 2);

        assertThat(world.isAliveAt(1, 1), is(true));
        assertThat(world.isAliveAt(2, 1), is(true));
        assertThat(world.isAliveAt(1, 2), is(true));
        assertThat(world.isAliveAt(2, 2), is(false));
        assertThat(world.isEmpty(), is(false));
    }

    @Test
    public void a_living_cell_can_die() {
        world.reviveAt(5, 5);
        assertThat(world.isAliveAt(5, 5), is(true));

        world.killAt(5, 5);
        assertThat(world.isAliveAt(5, 5), is(false));

        world.killAt(1, 1);
        assertThat(world.isAliveAt(1, 1), is(false));
    }

    @Test
    public void neighbours_can_be_counted() {
        world.reviveAt(1, 1);
        assertThat(world.countNeighboursFor(1, 1), is(0));
        assertThat(world.countNeighboursFor(2, 2), is(1));

        world.reviveAt(3, 3);
        assertThat(world.countNeighboursFor(2, 2), is(2));

        // revive six more neighbouring cells
        world.reviveAt(1, 2);
        world.reviveAt(1, 3);
        world.reviveAt(2, 1);
        world.reviveAt(2, 3);
        world.reviveAt(3, 1);
        world.reviveAt(3, 2);
        assertThat(world.countNeighboursFor(2, 2), is(8));
    }

    @Test
    public void a_dead_cell_with_exactly_3_neighbours_is_revived() {
        world.reviveAt(1, 2);
        world.reviveAt(1, 3);
        world.reviveAt(2, 1);
        assertThat(world.countNeighboursFor(2, 2), is(3));

        world.computeNextGeneration();
        assertThat(world.isAliveAt(2, 2), is(true));
    }

    @Test
    public void blinker() {
        world.reviveAt(2, 1);
        world.reviveAt(2, 2);
        world.reviveAt(2, 3);

        world.prettyPrint();
        world.computeNextGeneration();
        System.out.println();
        world.prettyPrint();

        assertThat(world.isAliveAt(2, 1), is(false));
        assertThat(world.isAliveAt(2, 3), is(false));
        assertThat(world.isAliveAt(2, 2), is(true));
        assertThat(world.isAliveAt(1, 2), is(true));
        assertThat(world.isAliveAt(3, 2), is(true));

        world.computeNextGeneration();
        System.out.println();
        world.prettyPrint();
    }

}
