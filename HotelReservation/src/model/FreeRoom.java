package model;

public class FreeRoom extends Room {

    public FreeRoom(Room room) {
        super(room.getRoomNumber(), 0.0, room.getRoomType(), room.isFree());
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
