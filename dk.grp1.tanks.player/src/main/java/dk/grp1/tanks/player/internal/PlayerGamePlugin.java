package dk.grp1.tanks.player.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.CirclePart;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.services.IGamePluginService;


public class PlayerGamePlugin implements IGamePluginService {

    private float playerRadius = 5f;

    @Override
    public void start(World world, GameData gameData) {
        Entity player = createPlayer(gameData);
        world.addEntity(player);
    }

    private Entity createPlayer(GameData gameData) {
        Player player = new Player();
        //player.add(new CirclePart(gameData.getDisplayWidth()/2, gameData.getDisplayHeight()/2, playerRadius));
        player.add(new CirclePart(40, 30, playerRadius));
        player.add(new PhysicsPart(5000f,-9.82f));
        player.add(new ControlPart(200));
        player.add(new LifePart());
        player.add(new PositionPart(200f,gameData.getGameHeight()/2+50, 0));
        player.add(new ShapePart());
        player.add(new CollisionPart(true,0));
        player.add(new MovementPart(100,100));
        return player;
    }

    @Override
    public void stop(World world, GameData gameData) {
        for (Entity player : world.getEntities(Player.class)) {
            world.removeEntity(player);
        }
    }
}
