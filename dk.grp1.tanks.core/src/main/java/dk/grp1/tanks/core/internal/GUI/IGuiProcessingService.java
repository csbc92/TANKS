package dk.grp1.tanks.core.internal.GUI;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;

public interface IGuiProcessingService {
    /**
     * Draws a gui element
     * @param world
     * @param gameData
     * @param spriteBatch
     */
    void draw(World world, GameData gameData, SpriteBatch spriteBatch);

    /**
     * Dispose should be called when the class should never be used again
     */
    void dispose();

}
