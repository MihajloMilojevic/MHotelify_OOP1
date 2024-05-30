package controllers;

import java.util.ArrayList;

import app.AppState;
import database.SelectCondition;
import exceptions.DuplicateIndexException;
import exceptions.NoElementException;
import models.Model;
import models.Room;
import models.RoomAddition;
import models.RoomType;

public class RoomController {

	public static ArrayList<Room> getRooms() {
		ArrayList<Room> rooms = AppState.getInstance().getDatabase().getRooms().select(new SelectCondition() {
			
			@Override
			public boolean check(Model row) {
				return !row.isDeleted();
			}
		});
		rooms.sort((r1, r2) -> r1.getNumber() - r2.getNumber());
		return rooms;
	}

	public static ControllerActionStatus addRoom(Room room) { 
		try {
			if (room == null || !room.isValid()) {
				return ControllerActionStatus.INCOPLETE_DATA;
			}
			AppState.getInstance().getDatabase().getRooms().insert(room);
			return ControllerActionStatus.SUCCESS;
		} catch (DuplicateIndexException e) {
			return ControllerActionStatus.DUPLICATE_INDEX;
		} catch (Exception e) {
			return ControllerActionStatus.ERROR;
		}
	}

	public static ControllerActionStatus updateRoom(Room room)  {
		try {
			if (room == null || !room.isValid()) {
				return ControllerActionStatus.INCOPLETE_DATA;
			}
			AppState.getInstance().getDatabase().getRooms().update(room);
			return ControllerActionStatus.SUCCESS;
		} catch (NoElementException e) {
			return ControllerActionStatus.NO_RECORD;
		} catch (Exception e) {
			return ControllerActionStatus.ERROR;
		}
	}

	public static ControllerActionStatus deleteRoom(Room room)  {
		try {
			if (room == null) {
				return ControllerActionStatus.INCOPLETE_DATA;
			}
			AppState.getInstance().getDatabase().getRooms().delete(room);
			return ControllerActionStatus.SUCCESS;
		} catch (Exception e) {
			return ControllerActionStatus.ERROR;
		}
	}

	public static ControllerActionStatus addRoomAddition(RoomAddition roomAddition)  {
		try {
			if (roomAddition == null || !roomAddition.isValid()) {
				return ControllerActionStatus.INCOPLETE_DATA;
			}
			AppState.getInstance().getDatabase().getRoomAdditions().insert(roomAddition);
			return ControllerActionStatus.SUCCESS;
		} catch (DuplicateIndexException e) {
            return ControllerActionStatus.DUPLICATE_INDEX;
        } catch (Exception e) {
            return ControllerActionStatus.ERROR;
        }
	}

	public static ControllerActionStatus updateRoomAddition(RoomAddition roomAddition) {
		try {
			if (roomAddition == null || !roomAddition.isValid()) {
				return ControllerActionStatus.INCOPLETE_DATA;
			}
			ArrayList<Room> rooms = AppState.getInstance().getDatabase().getRooms().select(new SelectCondition() {

				@Override
				public boolean check(Model row) {
					Room room = (Room) row;
					return room.getRoomAdditions().stream().map(RoomAddition::getId).toList().contains(roomAddition.getId());
				}
			});
			rooms.forEach(room -> {
				ArrayList<RoomAddition> newRoomAdditions = new ArrayList<>();
				for (RoomAddition ra : room.getRoomAdditions()) {
					if (ra.getId().equals(roomAddition.getId())) {
						newRoomAdditions.add(roomAddition);
					} else {
						newRoomAdditions.add(ra);
					}
				}
				room.setRoomAdditions(newRoomAdditions);
				ControllerActionStatus status = updateRoom(room);
				if (status != ControllerActionStatus.SUCCESS) {
					throw new RuntimeException("Error updating room");
				}
			});
			AppState.getInstance().getDatabase().getRoomAdditions().update(roomAddition);
			return ControllerActionStatus.SUCCESS;
		} catch (NoElementException e) {
			return ControllerActionStatus.NO_RECORD;
		} catch (Exception e) {
			return ControllerActionStatus.ERROR;
		}
	}

	public static ControllerActionStatus deleteRoomAddition(RoomAddition roomAddition) {
		try {
			if (roomAddition == null) {
				return ControllerActionStatus.INCOPLETE_DATA;
			}
			AppState.getInstance().getDatabase().getRooms().select(new SelectCondition() {

				@Override
				public boolean check(Model row) {
					Room room = (Room) row;
					return room.getRoomAdditions().contains(roomAddition);
				}
			}).forEach(room -> {
				room.removeRoomAddition(roomAddition);
				ControllerActionStatus status = updateRoom(room);
				if (status != ControllerActionStatus.SUCCESS) {
					throw new RuntimeException("Error updating room");
				}
			});
			AppState.getInstance().getDatabase().getRoomAdditions().delete(roomAddition);
			return ControllerActionStatus.SUCCESS;
		} catch (Exception e) {
			return ControllerActionStatus.ERROR;
		}
	}

	public static ControllerActionStatus addRoomType(RoomType roomType)  {
		try {
			if (roomType == null || !roomType.isValid()) {
				return ControllerActionStatus.INCOPLETE_DATA;
			}
			AppState.getInstance().getDatabase().getRoomTypes().insert(roomType);
			return ControllerActionStatus.SUCCESS;
		} catch (DuplicateIndexException e) {
			return ControllerActionStatus.DUPLICATE_INDEX;
		} catch (Exception e) {
			return ControllerActionStatus.ERROR;
		}
	}

	public static ControllerActionStatus updateRoomType(RoomType roomType)  {
		try {
			if (roomType == null || !roomType.isValid()) {
				return ControllerActionStatus.INCOPLETE_DATA;
			}
			AppState.getInstance().getDatabase().getRooms().select(new SelectCondition() {

				@Override
				public boolean check(Model row) {
					Room room = (Room) row;
					return room.getType().getId().equals(roomType.getId());
				}
			}).forEach(room -> {
				room.setType(roomType);
				ControllerActionStatus status = updateRoom(room);
				if (status != ControllerActionStatus.SUCCESS) {
					throw new RuntimeException("Error updating room");
				}
			});
			AppState.getInstance().getDatabase().getRoomTypes().update(roomType);
			return ControllerActionStatus.SUCCESS;
		} catch (NoElementException e) {
			return ControllerActionStatus.NO_RECORD;
		} catch (Exception e) {
			return ControllerActionStatus.ERROR;
		}
	}
	
	public static ControllerActionStatus deleteRoomType(RoomType roomType) {
		try {
			if (roomType == null) {
				return ControllerActionStatus.INCOPLETE_DATA;
			}
			if (isRoomTypeUsed(roomType)) {
				return ControllerActionStatus.IN_USE;
			}
			AppState.getInstance().getDatabase().getRoomTypes().delete(roomType);
			return ControllerActionStatus.SUCCESS;
		} catch (Exception e) {
            return ControllerActionStatus.ERROR;
        }
	}
	
	public static boolean isRoomTypeUsed(RoomType roomType) {
		if (roomType == null) return false;
		if (roomType.isDeleted()) return false;
		return AppState.getInstance().getDatabase().getRooms().select(new SelectCondition() {

			@Override
			public boolean check(Model row) {
				Room room = (Room) row;
				return room.getType().getId().equals(roomType.getId()) && !room.isDeleted();
			}
		}).size() > 0;
	}
	
 	public static ArrayList<RoomType> getRoomTypes() {
		return AppState.getInstance().getDatabase().getRoomTypes().select(new SelectCondition() {

			@Override
			public boolean check(Model row) {
				return !row.isDeleted();
			}
		});
	}
	public static ArrayList<RoomAddition> getRoomAdditions() {
		return AppState.getInstance().getDatabase().getRoomAdditions().select(new SelectCondition() {

			@Override
			public boolean check(Model row) {
				return !row.isDeleted();
			}
		});
	}
	public static RoomAddition getRoomAdditionByName(String name) {
		return AppState.getInstance().getDatabase().getRoomAdditions().selectByIndex("name", name);
	}
}
