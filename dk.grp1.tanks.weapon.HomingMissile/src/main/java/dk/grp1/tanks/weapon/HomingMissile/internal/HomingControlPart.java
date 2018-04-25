package dk.grp1.tanks.weapon.HomingMissile.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.IEntityPart;
import dk.grp1.tanks.common.data.parts.MovementPart;
import dk.grp1.tanks.common.data.parts.PositionPart;
import dk.grp1.tanks.common.utils.Vector2D;

import java.util.List;

public class HomingControlPart implements IEntityPart {

    private List<Vector2D> path;
    private int goingToIndex;

    public HomingControlPart(){

    }

    public HomingControlPart(List<Vector2D> path){

        this.path = path;
    }
    @Override
    public void processPart(Entity entity, GameData gameData, World world) {
        if(isPastPoint(entity)){
            goingToIndex++;
            setNewDirection(entity);
        }

    }

    private void setNewDirection(Entity entity) {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        MovementPart movementPart = entity.getPart(MovementPart.class);



    }

    private boolean isPastPoint(Entity entity) {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        MovementPart movementPart = entity.getPart(MovementPart.class);

        if(movementPart.getVelocity().getX() == 0){

            //Code goes here
        }else if(movementPart.getVelocity().getY() == 0){
            //Code goes here
        }else{
            //Code goes here
        }

        return false;
    }

    private float norm(float value){
        return 1;
    }
}