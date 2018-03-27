package dk.grp1.tanks.core.internal.GUI;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.CannonPart;
import dk.grp1.tanks.common.data.parts.CirclePart;
import dk.grp1.tanks.common.data.parts.InventoryPart;
import dk.grp1.tanks.common.data.parts.PositionPart;
import javafx.geometry.Pos;

public class OnScreenText implements IGuiProcessingService {

    private SpriteBatch batch = new SpriteBatch();
    private BitmapFont font;
    private OrthographicCamera camera;
    private BitmapFont.TextBounds textBounds;

    /**
     * Draws various text values on the screen
     *
     * @param world
     * @param gameData
     */
    @Override
    public void draw(World world, GameData gameData) {
        turnText(gameData);
        for (Entity entity : world.getEntities()) {
            InventoryPart inventoryPart = entity.getPart(InventoryPart.class);
            if (inventoryPart != null) {
                weaponText(entity, inventoryPart);
                angleText(entity);
                firepowerText(entity);
            }
        }
    }

    private void firepowerText(Entity entity) {
        font = new BitmapFont();
        font.getData().scaleX = 0.5f;
        font.getData().scaleY = 0.5f;
        CannonPart cannonPart = entity.getPart(CannonPart.class);
        int firepower = Math.round(cannonPart.getPreviousFirepower());
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        String fire = "Firepower: " + firepower;
        textBounds = font.getBounds(fire);
        font.draw(batch, fire, cannonPart.getJointX() - textBounds.width / 2,
                cannonPart.getJointY() - 30);
        batch.end();
        font.dispose();
    }

    @Override
    public void setCam(OrthographicCamera camera) {
        this.camera = camera;
    }

    private void angleText(Entity entity){
        font = new BitmapFont();
        font.getData().scaleX = 0.5f;
        font.getData().scaleY = 0.5f;
        CannonPart cannonPart = entity.getPart(CannonPart.class);
        double angle = cannonPart.getPreviousAngle() * 180 / 3.1415f;
        angle = Math.ceil(angle);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        String angleString = "Angle: " + angle;
        textBounds = font.getBounds(angleString);
        font.draw(batch, angleString, cannonPart.getJointX() - textBounds.width / 2,
                cannonPart.getJointY() - 20);
        batch.end();
        font.dispose();
    }

    private void weaponText(Entity entity, InventoryPart inventoryPart) {
        String weaponText;
        try {
            weaponText = inventoryPart.getCurrentWeapon().getName();
        } catch (NullPointerException e) {
            weaponText = "None";
        }
        font = new BitmapFont();
        font.getData().scaleX = 0.5f;
        font.getData().scaleY = 0.5f;
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        CirclePart circlePart = entity.getPart(CirclePart.class);
        textBounds = font.getBounds(weaponText);
        font.draw(batch, weaponText, circlePart.getCentreX() - textBounds.width / 2, circlePart.getCentreY() - 10);
        batch.end();
        font.dispose();
    }


    private void turnText(GameData gameData) {
        font = new BitmapFont();
        font.getData().scaleX = 0.5f;
        font.getData().scaleY = 0.5f;
        String turnText = "Turn: ";
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, turnText, 10, gameData.getGameHeight() - 10);
        batch.end();
        font.dispose();
    }
}