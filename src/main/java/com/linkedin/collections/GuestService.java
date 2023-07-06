package com.linkedin.collections;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GuestService {

	private List<Guest> checkinList = new ArrayList<>(100);

	public static List<Guest> filterByFavoriteRoom(List<Guest> guests, Room room) {

		/*
		 *  1. Returns a new collection that contains guests from the provided collection
		 *  who have indicated the provided room as the first preference in their preferred
		 *  room list. 
		 */

		return  guests.stream().filter(guest -> guest.getPreferredRooms().indexOf(room) == 0).collect(Collectors.toList());
		

	}

	public void checkIn(Guest guest) {
		
		/*
		 *  2. Adds a guest to the checkinList, placing members of the loyalty program
		 *  ahead of those guests not in the program. Otherwise, guests are arranged in the
		 *  order they were inserted.
		 */

		List<Guest> loyalMembers=checkinList.stream().filter(Guest::isLoyaltyProgramMember).collect(Collectors.toList());
		List<Guest> loyalMembersNot=checkinList.stream().filter(guestNot -> !guestNot.isLoyaltyProgramMember()).collect(Collectors.toList());

	if (guest.isLoyaltyProgramMember())
	{
		loyalMembers.add(guest);
		loyalMembers.addAll(loyalMembersNot);
		checkinList.clear();
		checkinList.addAll(loyalMembers);
	}
	else
	{
		loyalMembersNot.addAll(loyalMembers);
		loyalMembersNot.add(guest);
		checkinList.clear();
		checkinList.addAll(loyalMembersNot);
	}



	}
	
	public void swapPosition(Guest guest1, Guest guest2) {
		
		/*
		 *  3.  Swaps the position of the two provided guests within the checkinList.
		 *  If guests are not currently in the list no action is required.
		 */

		int indexGuest1=checkinList.indexOf(guest1);
		int indexGuest2=checkinList.indexOf(guest2);

		if (-1 != indexGuest1 && -1 != indexGuest2)
		{
			checkinList.set(indexGuest1,guest2);
			checkinList.set(indexGuest2,guest1);


		}



	}

	public List<Guest> getCheckInList() {
		return List.copyOf(this.checkinList);
	}
}
