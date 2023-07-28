package sn.sir.booking.me_sir_booking_app.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sn.sir.booking.me_sir_booking_app.domain.Hotel;
import sn.sir.booking.me_sir_booking_app.domain.Reservation;
import sn.sir.booking.me_sir_booking_app.domain.Room;
import sn.sir.booking.me_sir_booking_app.model.RoomDTO;
import sn.sir.booking.me_sir_booking_app.repos.HotelRepository;
import sn.sir.booking.me_sir_booking_app.repos.ReservationRepository;
import sn.sir.booking.me_sir_booking_app.repos.RoomRepository;
import sn.sir.booking.me_sir_booking_app.util.NotFoundException;


@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final ReservationRepository reservationRepository;

    public RoomService(final RoomRepository roomRepository, final HotelRepository hotelRepository,
            final ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<RoomDTO> findAll() {
        final List<Room> rooms = roomRepository.findAll(Sort.by("id"));
        return rooms.stream()
                .map(room -> mapToDTO(room, new RoomDTO()))
                .toList();
    }

    public RoomDTO get(final Long id) {
        return roomRepository.findById(id)
                .map(room -> mapToDTO(room, new RoomDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final RoomDTO roomDTO) {
        final Room room = new Room();
        mapToEntity(roomDTO, room);
        return roomRepository.save(room).getId();
    }

    public void update(final Long id, final RoomDTO roomDTO) {
        final Room room = roomRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(roomDTO, room);
        roomRepository.save(room);
    }

    public void delete(final Long id) {
        roomRepository.deleteById(id);
    }

    private RoomDTO mapToDTO(final Room room, final RoomDTO roomDTO) {
        roomDTO.setId(room.getId());
        roomDTO.setNumber(room.getNumber());
        roomDTO.setDescription(room.getDescription());
        roomDTO.setTypeRoom(room.getTypeRoom());
        roomDTO.setDateCreated(room.getDateCreated());
        roomDTO.setLastUpdated(room.getLastUpdated());
        roomDTO.setHotel(room.getHotel() == null ? null : room.getHotel().getId());
        roomDTO.setReservation(room.getReservation() == null ? null : room.getReservation().getId());
        return roomDTO;
    }

    private Room mapToEntity(final RoomDTO roomDTO, final Room room) {
        room.setNumber(roomDTO.getNumber());
        room.setDescription(roomDTO.getDescription());
        room.setTypeRoom(roomDTO.getTypeRoom());
        room.setDateCreated(roomDTO.getDateCreated());
        room.setLastUpdated(roomDTO.getLastUpdated());
        final Hotel hotel = roomDTO.getHotel() == null ? null : hotelRepository.findById(roomDTO.getHotel())
                .orElseThrow(() -> new NotFoundException("hotel not found"));
        room.setHotel(hotel);
        final Reservation reservation = roomDTO.getReservation() == null ? null : reservationRepository.findById(roomDTO.getReservation())
                .orElseThrow(() -> new NotFoundException("reservation not found"));
        room.setReservation(reservation);
        return room;
    }

}
