-- 좌석현황 업데이트
/* 예약 완료 누르면(새로운 reservation이 생성되면) trigger를 사용하여 
   예약된 좌석의 seatId와 flightId를 확인하여 좌석의 isAvailable 을 false로 바꾼다.*/
DELIMITER //
CREATE TRIGGER UpdateSeatIsAvailableAfterReservation
AFTER INSERT ON reservation
FOR EACH ROW
BEGIN
	UPDATE Seat
    SET isAvailable = false
    WHERE flightId = NEW.flightId AND seatNum = NEW.seatNum;
END //
DELIMITER ;
