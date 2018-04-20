package dk.grp1.tanks.weapon.HomingMissile.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.eventManager.events.SoundEvent;
import dk.grp1.tanks.common.services.IWeapon;
import dk.grp1.tanks.common.utils.Vector2D;

public class HomingMissileWeapon implements IWeapon {

    private final String name = "Big Shot";
    private final String description = "Fires a single big shot";
    private final String iconPath = "bigshot.png";
    private final String shootSoundPath= "boom.mp3";
    private final String texturePath = "bigshot.png";
    private final String explosionTexturePath = "explosion.png";
    private final int explosionTextureFrameRows = 6;
    private final int explosionTextureFrameCols = 8;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getIconPath() {
        return iconPath;
    }

    @Override
    public void shoot(Entity actor, GameData gameData, float firePower, World world) {
        HomingMissile wep = new HomingMissile();

        CannonPart cannonPart = actor.getPart(CannonPart.class);
        Vector2D cannonCentre = cannonPart.getMuzzleFaceCentre();
        wep.add(new PositionPart(cannonCentre.getX(),cannonCentre.getY(), cannonPart.getDirection()));
        Vector2D accelerationVector = cannonPart.getDirectionVector();
        accelerationVector.multiplyWithConstant(firePower);
        wep.add(new MovementPart(accelerationVector, 10000));
        wep.add(new ShapePart());
        wep.add(new CirclePart(cannonCentre.getX(),cannonCentre.getY(),2));
        wep.add(new PhysicsPart(30, -90.82f));
        wep.add(new CollisionPart(true,0));
        wep.add(new DamagePart(4,10));
        wep.add(new TexturePart(this.texturePath));
        wep.add(new ExplosionTexturePart(explosionTextureFrameCols,explosionTextureFrameRows,explosionTexturePath));
        SoundPart sounds = new SoundPart("missile.mp3","boom.mp3");
        wep.add(sounds);
        gameData.getEventManager().addEvent(new SoundEvent(wep,sounds.getShootSoundPath()));

        world.addEntity(wep);
    }
}