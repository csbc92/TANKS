package dk.grp1.tanks.weapon.BouncyBall.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.services.IWeapon;
import dk.grp1.tanks.common.utils.Vector2D;

public class BouncyBallWeapon implements IWeapon{

    private final String name = "BouncyBall";
    private final String description = "Shoots a bouncing ball";
    private final String iconPath = "bouncy_ball.png";
    private final String texturePath = "bouncy_ball.png";

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
    public void shoot(Entity actor, float firePower, World world) {
        BouncyBall BouncyBall = new BouncyBall();

        CannonPart cannonPart = actor.getPart(CannonPart.class);
        Vector2D cannonCentre = cannonPart.getMuzzleFaceCentre();
        BouncyBall.add(new PositionPart(cannonCentre.getX(),cannonCentre.getY(), cannonPart.getDirection()));
        Vector2D accelerationVector = cannonPart.getDirectionVector();
        accelerationVector.multiplyWithConstant(firePower);
        BouncyBall.add(new BouncyBallMovementPart(accelerationVector, 10000));
        BouncyBall.add(new BouncyBallExpirationPart(10));
        BouncyBall.add(new ShapePart());
        BouncyBall.add(new CirclePart(30,30,5));
        BouncyBall.add(new PhysicsPart(30, -90.82f));
        BouncyBall.add(new BouncyBallCollisionPart(true,0));
        BouncyBall.add(new DamagePart(3,1));
        BouncyBall.add(new TexturePart(this.texturePath));

        world.addEntity(BouncyBall);
    }
}