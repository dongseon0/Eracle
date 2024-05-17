-- 추가수하물 여부에 따라 totalPrice 업데이트
/* 예약 정보에서 수하물 추가 Y/N를 input으로 입력하면 수하물 추가가 되고, totalprice가 변경되는 메뉴*/
-- 일단 input 받는 것은 GUI에서 된다고 생각하고 코드 작성했습니다. 이후 수정이 필요할 수 있습니다!
DELIMITER //
CREATE PROCEDURE AddBaggage(
	IN p_reservationId BIGINT,
	IN p_additionalBaggage BOOLEAN
    )
    
BEGIN
  	-- 변수 선언
  	DECLARE v_basePrice INT;
    DECLARE v_additionalPrice INT;
    DECLARE v_totalPrice INT;
    
    -- 예약 정보 조회
    SELECT totalPrice, additionalBaggage INTO v_basePrice, v_additionalPrice
    FROM Reservation
    WHERE reservationId = p_reservationId;
    
    -- 추가 수하물
    IF p_additionalBaggage = true 
    THEN SET v_additionalPrice = 500000;
    ELSE
		  SET v_additionalPrice = 0;
    END IF;
    
    -- 총 가격 계산 업데이트
    SET v_totalPrice = v_basePrice + v_additionalPrice;
    
    -- 예약 정보 업데이트
    UPDATE Reservation
    SET additionalBaggage = p_additionalBaggage and totalPrice = v_totalPrice
    WHERE reservationId = p_reservationId;
    
    SELECT * FROM Reservation WHERE reservationId = p_reservationId;

END //

DELIMITER ;
