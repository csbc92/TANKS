package dk.grp1.tanks.common.data.parts;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.events.EndTurnEvent;

public class TurnPart implements IEntityPart {

    private static int turnPartCount;
    private int myTurnNumber;
    private static int currentTurnNumber;

    private float turnDuration = 5f;
    private float turnTimeRemaining = turnDuration;

    private boolean turnEndRequested;
    public TurnPart(){
        myTurnNumber = turnPartCount++;

    }

    public static void setCurrentTurnNumber(int currentTurnNumber) {
        TurnPart.currentTurnNumber = currentTurnNumber;
    }
    public static int getCurrentTurnNumber() {
       return currentTurnNumber;
    }


    @Override
    public void processPart(Entity entity, GameData gameData, World world) {
        if(isMyTurn()) {
            turnTimeRemaining -= gameData.getDelta();
            if (turnTimeRemaining < 0 ||turnEndRequested) {
                gameData.addEvent(new EndTurnEvent(entity));
                turnTimeRemaining = turnDuration;
                turnEndRequested = false;
            }
        }
    }

    public boolean isMyTurn() {
        return currentTurnNumber == myTurnNumber;
    }



    public int getMyTurnNumber() {
        return myTurnNumber;
    }

    public void endMyTurn(){
        turnEndRequested = true;
    }
}