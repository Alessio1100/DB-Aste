-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema aste
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema aste
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `aste` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `aste` ;

-- -----------------------------------------------------
-- Table `aste`.`amministratore`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aste`.`amministratore` (
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`username`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `aste`.`carta di credito`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aste`.`carta di credito` (
  `Numero` VARCHAR(16) NOT NULL,
  `Nome` VARCHAR(45) NOT NULL,
  `Cognome` VARCHAR(45) NOT NULL,
  `Scadenza` VARCHAR(5) NOT NULL,
  `CVV` VARCHAR(4) NOT NULL,
  PRIMARY KEY (`Numero`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `aste`.`categoria`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aste`.`categoria` (
  `Nome` VARCHAR(45) NOT NULL,
  `CategoriaGenitore` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`Nome`),
  INDEX `fk_categoria_genitore_idx` (`CategoriaGenitore` ASC) VISIBLE,
  CONSTRAINT `fk_categoria_categoria`
    FOREIGN KEY (`CategoriaGenitore`)
    REFERENCES `aste`.`categoria` (`Nome`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `aste`.`utente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aste`.`utente` (
  `CF` VARCHAR(16) NOT NULL,
  `Nome` VARCHAR(45) NOT NULL,
  `Cognome` VARCHAR(45) NOT NULL,
  `Data di nascita` DATE NOT NULL,
  `Città di nascita` VARCHAR(45) NOT NULL,
  `Via` VARCHAR(45) NOT NULL,
  `Civico` VARCHAR(6) NOT NULL,
  `Comune` VARCHAR(45) NOT NULL,
  `CAP` VARCHAR(5) NOT NULL,
  `Carta di Credito` VARCHAR(16) NOT NULL,
  `Username` VARCHAR(45) NOT NULL,
  `Password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`CF`),
  UNIQUE INDEX `Username_UNIQUE` (`Username` ASC) VISIBLE,
  INDEX `fk_cartadicredito_idx` (`Carta di Credito` ASC) VISIBLE,
  CONSTRAINT `fk_utente_cartadicredito`
    FOREIGN KEY (`Carta di Credito`)
    REFERENCES `aste`.`carta di credito` (`Numero`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `aste`.`oggetto in asta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aste`.`oggetto in asta` (
  `ID` VARCHAR(10) NOT NULL,
  `Descrizione` VARCHAR(45) NOT NULL,
  `Stato` VARCHAR(45) NOT NULL,
  `Base d'asta` FLOAT NOT NULL,
  `Dimensioni` VARCHAR(45) NOT NULL,
  `Inizio asta` DATETIME NOT NULL,
  `Durata asta` INT NOT NULL,
  `Termine asta` DATE NOT NULL,
  `Tipo` ENUM('in corso', 'venduto', 'non venduto') NOT NULL,
  `Prezzo di vendita` FLOAT NULL DEFAULT NULL,
  `Numero offerte` INT NULL DEFAULT NULL,
  `Valore max offerta` FLOAT NULL DEFAULT NULL,
  `Categoria` VARCHAR(45) NOT NULL,
  `Utente` VARCHAR(16) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_oggetto_utene_idx` (`Utente` ASC) VISIBLE,
  INDEX `fk_oggetto_categoria_idx` (`Categoria` ASC) VISIBLE,
  INDEX `idx_oggetto_in_asta_termine_asta` (`Termine asta` ASC) VISIBLE,
  CONSTRAINT `fk_oggetto_categoria`
    FOREIGN KEY (`Categoria`)
    REFERENCES `aste`.`categoria` (`Nome`),
  CONSTRAINT `fk_oggetto_utente`
    FOREIGN KEY (`Utente`)
    REFERENCES `aste`.`utente` (`CF`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `aste`.`offerta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aste`.`offerta` (
  `Utente` VARCHAR(16) NOT NULL,
  `Importo` FLOAT NOT NULL,
  `Oggetto in asta` VARCHAR(10) NOT NULL,
  `Data` DATE NOT NULL,
  `Ora` TIME NOT NULL,
  `Importo massimo automatico` FLOAT NULL DEFAULT NULL,
  PRIMARY KEY (`Utente`, `Oggetto in asta`, `Importo`),
  INDEX `fk_offerta_oggetto_idx` (`Oggetto in asta` ASC) VISIBLE,
  INDEX `idx_offerta_importo` (`Importo` ASC) VISIBLE,
  CONSTRAINT `fk_offerta_oggetto`
    FOREIGN KEY (`Oggetto in asta`)
    REFERENCES `aste`.`oggetto in asta` (`ID`),
  CONSTRAINT `fk_offerta_utente`
    FOREIGN KEY (`Utente`)
    REFERENCES `aste`.`utente` (`CF`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

USE `aste` ;

-- -----------------------------------------------------
-- procedure admin_registration_procedure
-- -----------------------------------------------------

DELIMITER $$
USE `aste`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `admin_registration_procedure`( in var_username VARCHAR(45), in var_password VARCHAR(45) )
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;

    SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
    START TRANSACTION;
    
 -- Controllo se il username esiste già nella tabella utente
	IF (SELECT COUNT(*) FROM utente WHERE username = var_username) > 0 OR (SELECT COUNT(*) FROM amministratore WHERE username = var_username) > 0 THEN
	   SIGNAL SQLSTATE '45040' SET MESSAGE_TEXT = "Username already exists ";
	END IF;
	INSERT INTO amministratore(username, `password`) VALUES (var_username, md5(var_password));
    
    COMMIT;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- function check_cap
-- -----------------------------------------------------

DELIMITER $$
USE `aste`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `check_cap`( CAP varchar(5) ) RETURNS tinyint(1)
    DETERMINISTIC
BEGIN
		IF CAP regexp'^[0-9]{5}$' then 
			return true ;
        ELSE 
			return false ;
		END IF;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- function check_cf
-- -----------------------------------------------------

DELIMITER $$
USE `aste`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `check_cf`(CF varchar(16)) RETURNS tinyint(1)
    DETERMINISTIC
BEGIN
	IF CF regexp '([A-Z]{6}[0-9LMNPQRSTUV]{2}[ABCDEHLMPRST]{1}[0-9LMNPQRSTUV]{2}[A-Z]{1}[0-9LMNPQRSTUV]{3}[A-Z]{1})$|([0-9]{11})$' then 
		return true;
	ELSE 
		return false;
	END IF;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- function check_civico
-- -----------------------------------------------------

DELIMITER $$
USE `aste`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `check_civico`(civico varchar(6)) RETURNS tinyint(1)
    DETERMINISTIC
BEGIN
	if civico regexp '^[0-9]{1,5}[a-zA-Z]{0,1}$' then 
		return true;
	else 
		return false;
	end if ;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- function check_credit_card
-- -----------------------------------------------------

DELIMITER $$
USE `aste`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `check_credit_card`(creditcard varchar(16)) RETURNS tinyint(1)
    DETERMINISTIC
BEGIN
	if creditcard regexp '[0-9]{16}$' then
		return true;
	else
		return false;
	END IF ;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- function check_cvv
-- -----------------------------------------------------

DELIMITER $$
USE `aste`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `check_cvv`( cvv varchar(4) ) RETURNS tinyint(1)
    DETERMINISTIC
BEGIN
		IF cvv regexp '^[0-9]{3,4}$' then 
			return true;
		ELSE 
			return false;
		END IF;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- function check_expire_date
-- -----------------------------------------------------

DELIMITER $$
USE `aste`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `check_expire_date`(scadenza varchar(5)) RETURNS tinyint(1)
    DETERMINISTIC
BEGIN
		if scadenza regexp '(0[1-9]|1[0-2])-[0-9]{2}$' then
			return true;
		else
			return false;
		end if;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- function check_valid_expire
-- -----------------------------------------------------

DELIMITER $$
USE `aste`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `check_valid_expire`(scadenza varchar(5)) RETURNS tinyint(1)
    DETERMINISTIC
BEGIN
    DECLARE expiry_date DATE;
    DECLARE curr_date DATE;

    -- Converte la data di scadenza nel primo giorno del mese successivo
    SET expiry_date = STR_TO_DATE(CONCAT('01-', scadenza), '%d-%m-%y');

    -- Ottiene la data corrente
    SET curr_date = CURDATE();

    -- Confronta le date
    IF expiry_date < curr_date THEN
        RETURN false; -- Carta scaduta
    ELSE
        RETURN true; -- Carta valida
    END IF;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure delete_category
-- -----------------------------------------------------

DELIMITER $$
USE `aste`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_category`( in var_name VARCHAR(45) )
BEGIN
		DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN 
			  ROLLBACK;
              RESIGNAL;
		END;
        
        SET TRANSACTION ISOLATION LEVEL READ COMMITTED ;
        START TRANSACTION;
        
        DELETE FROM categoria WHERE nome = var_name;
        
        IF ROW_COUNT() = 0 THEN
			ROLLBACK;
			SIGNAL SQLSTATE '45070' SET MESSAGE_TEXT = 'Category does not exist';
		ELSE
			COMMIT;
		END IF;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure delete_parent_category
-- -----------------------------------------------------

DELIMITER $$
USE `aste`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_parent_category`( in var_name VARCHAR(45) )
BEGIN
		DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN 
			  ROLLBACK;
              RESIGNAL;
		END;
        
        SET TRANSACTION ISOLATION LEVEL REPEATABLE READ ;
        START TRANSACTION;
        
        UPDATE categoria 
        SET categoriaGenitore = null
        WHERE nome = var_name; 
        
        IF ROW_COUNT() = 0 THEN
			ROLLBACK;
			SIGNAL SQLSTATE '45070' SET MESSAGE_TEXT = 'Category does not exist';
		ELSE
			COMMIT;
		END IF;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure get_offered_items
-- -----------------------------------------------------

DELIMITER $$
USE `aste`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_offered_items`( in var_user VARCHAR(16) )
BEGIN
	  DECLARE EXIT HANDLER FOR SQLEXCEPTION 
      BEGIN 
		    ROLLBACK;
            RESIGNAL;
	  END;
      
      SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
      START TRANSACTION;
      
      SELECT ID, Descrizione, Stato, `Base d'asta`, Dimensioni, `Inizio asta`, `Termine asta`,`Numero offerte`,`Valore max offerta`,Categoria
      FROM  `oggetto in asta`
      JOIN   offerta as O ON `oggetto in asta`.ID = O.`Oggetto in asta`
      WHERE  `oggetto in asta`.tipo = 'in corso'
      AND    `Termine asta` > CURRENT_DATE()
      AND 	  var_user IN (SELECT Utente FROM offerta WHERE O.Utente = offerta.Utente ) 
			 UNION 
      SELECT ID, Descrizione, Stato, `Base d'asta`, Dimensioni, `Inizio asta`, `Termine asta`,`Numero offerte`,`Valore max offerta`,Categoria
      FROM  `oggetto in asta`
      JOIN   offerta as O ON `oggetto in asta`.ID = O.`Oggetto in asta`
      WHERE  `oggetto in asta`.tipo = 'in corso'
      AND    `Termine asta` = CURRENT_DATE()
      AND 	 `Inizio asta`- CURRENT_TIME > 0
      AND 	 var_user IN (SELECT Utente FROM offerta WHERE O.Utente = offerta.Utente );
      
      COMMIT;
      
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure get_purchased_items
-- -----------------------------------------------------

DELIMITER $$
USE `aste`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_purchased_items`(in var_user VARCHAR(16))
BEGIN
	  DECLARE EXIT HANDLER FOR SQLEXCEPTION
      BEGIN 
		    ROLLBACK;
            RESIGNAL;
	  END;
      
      SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
      START TRANSACTION;
      
      SELECT ID, Descrizione, Stato, Dimensioni, `Termine asta`,`Prezzo di vendita`,Categoria
      FROM 	 `oggetto in asta` 
      WHERE  Utente = var_user 
      AND    Tipo = 'venduto';
      
      COMMIT;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure get_user_procedure
-- -----------------------------------------------------

DELIMITER $$
USE `aste`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_user_procedure`(in var_username VARCHAR(45))
BEGIN
		SELECT cf, nome, cognome, `data di nascita`,
		`città di nascita`,via,civico,comune,cap, `carta di credito`
		FROM utente 
		WHERE username = var_username;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure insert_item_procedure
-- -----------------------------------------------------

DELIMITER $$
USE `aste`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_item_procedure`(
  	    in var_description VARCHAR(45), 
	    in var_stato       VARCHAR(45), 
	    in var_base        FLOAT, 
	    in var_dim         VARCHAR(45), 
	    in var_duration    INTEGER, 
	    in var_category    VARCHAR(45))
BEGIN
		DECLARE var_id VARCHAR(10);
        DECLARE var_start DATETIME;	
        DECLARE var_finish DATE;
        DECLARE var_type ENUM('in corso','venduto','non venduto');
        DECLARE var_offer_n INTEGER;
		DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN 
			  ROLLBACK;
              RESIGNAL;
		END;
        
        
        SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
        START TRANSACTION ;
        
        SET var_id = SUBSTRING(REPLACE(UUID(), '-', ''), 1, 10);
        SET var_start = current_timestamp();	
        SET var_finish = DATE_ADD(var_start, interval var_duration day);
        SET var_type = 'in corso';
        SET var_offer_n = 0;
        
        INSERT INTO `oggetto in asta`(ID, Descrizione, Stato, `Base d'asta`, Dimensioni, `Inizio asta`, `Durata asta`, `Termine asta`, Tipo, `Numero offerte`, Categoria)
        VALUES (var_id, var_description, var_stato, var_base, var_dim, var_start, var_duration, var_finish, var_type, var_offer_n, var_category);
       
       COMMIT;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure insert_parent_category
-- -----------------------------------------------------

DELIMITER $$
USE `aste`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_parent_category`( in var_name VARCHAR(45) )
BEGIN
		DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN 
			ROLLBACK;
			RESIGNAL;
        END;
        
		SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
        START TRANSACTION;
        
			INSERT INTO categoria(nome) VALUES (var_name);
        
        COMMIT;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure insert_sub_category
-- -----------------------------------------------------

DELIMITER $$
USE `aste`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_sub_category`( in var_name VARCHAR(45), in var_parent VARCHAR(45))
BEGIN
		DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN 
			ROLLBACK;
			RESIGNAL;
        END;
        
		SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;
        START TRANSACTION;
        
			INSERT INTO categoria(nome, categoriaGenitore) VALUES (var_name, var_parent);
        
        COMMIT;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure login
-- -----------------------------------------------------

DELIMITER $$
USE `aste`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `login`(in var_username VARCHAR(45), in var_password VARCHAR(45), out var_role INT)
BEGIN
    DECLARE is_admin INT;
    DECLARE is_user INT;
	
    SELECT COUNT(*) INTO is_admin
    FROM aste.amministratore 
    WHERE username = var_username AND password = md5(var_password);

    SELECT COUNT(*) INTO is_user
    FROM aste.utente 
    WHERE username = var_username AND password = md5(var_password);
	
    IF is_user = 1 THEN
        SET var_role = 1;
    ELSEIF is_admin = 1 THEN
        SET var_role = 2;
    ELSE 
		SET var_role = 3;
    END IF;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure make_automatic_offer
-- -----------------------------------------------------

DELIMITER $$
USE `aste`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `make_automatic_offer`(in var_user VARCHAR(16), in var_amount FLOAT, in var_object VARCHAR(10), in var_controfferta FLOAT)
BEGIN	
		DECLARE var_offer_date DATE;
        DECLARE var_offer_time TIME;
        DECLARE var_card_expire_date VARCHAR(5);
        DECLARE var_curr_max_offer FLOAT;
        DECLARE var_object_type ENUM('in corso','venduto','non venduto');
        DECLARE var_offers_number INT;
        DECLARE var_new_offers_number INT;
        DECLARE var_finish_auction DATE;
        DECLARE var_start_auction DATETIME;
		DECLARE var_base_price FLOAT;
        -- CONTROFFERTA
		DECLARE var_user_controfferta VARCHAR(16);
        DECLARE var_amount_controfferta FLOAT;
	    DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN 
			  ROLLBACK;
              RESIGNAL;
	    END;
        
        SET TRANSACTION ISOLATION LEVEL SERIALIZABLE; 
        START TRANSACTION;
        
        SELECT `Base d'asta`, `Numero offerte`, `Valore max offerta`, `Termine asta`, `Inizio asta`, Tipo
        INTO   var_base_price, var_offers_number, var_curr_max_offer, var_finish_auction, var_start_auction, var_object_type
        FROM   `oggetto in asta`
        WHERE  	ID = var_object;
        
        IF var_controfferta < var_amount then 
		   SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "Automatic offer amount can't be less than current offer ";
	    END IF;
        
        IF var_amount % 0.5 <> 0 then
		   SIGNAL SQLSTATE '45010' SET MESSAGE_TEXT = "Offer amount not valid";
		END IF;  
        
        IF var_amount <= var_curr_max_offer then
		   SIGNAL SQLSTATE '45011' SET MESSAGE_TEXT = "Offer amount too low";
        END IF;
        
		IF var_offers_number = 0 then
		   IF var_amount < var_base_price then
			  SIGNAL SQLSTATE '45012' SET MESSAGE_TEXT = "Offer amount less than start price ";
		   END IF;
	    END IF;
        
        IF var_object_type <> 'in corso' then 
		   SIGNAL SQLSTATE '45013' SET MESSAGE_TEXT = "Auction finished";
        END IF;
        
        IF var_finish_auction < CURRENT_DATE() then 
           SIGNAL SQLSTATE '45013' SET MESSAGE_TEXT = "Auction finished";
		END IF;
         
		IF var_finish_auction = CURRENT_DATE() and TIME(var_start_auction) < CURRENT_TIME() then 
            SIGNAL SQLSTATE '45013' SET MESSAGE_TEXT = "Auction finished";
		END IF;
         
		SELECT Scadenza INTO var_card_expire_date
        FROM `carta di credito` JOIN Utente ON `carta di credito`.Numero = Utente.`Carta di Credito`
        WHERE CF = var_user;
	-- il pagamento avviene alla fine dell'asta, quindi è importante che la carta non scada prima della fine di essa 
        IF STR_TO_DATE(CONCAT('01-',var_card_expire_date), '%d-%m-%y') < var_finish_auction then 
		   SIGNAL SQLSTATE '45007' SET MESSAGE_TEXT = " Expired Credit Card ";
		END IF;
        
        SET var_offer_date = CURRENT_DATE();
        SET var_offer_time = CURRENT_TIME();
        
        SELECT `Numero offerte` INTO var_offers_number
        FROM   `oggetto in asta`
        WHERE  ID = var_object;
        
        SET var_new_offers_number = var_offers_number + 1;
	 -- inserimento offerta 
        INSERT INTO offerta( Utente, Importo, `Oggetto in asta`, `Data`, Ora, `importo massimo automatico` )
        VALUES ( var_user, var_amount, var_object, var_offer_date, var_offer_time, var_controfferta );
	 -- aggiorno numero offerte e max offerta
		UPDATE `oggetto in asta`
        SET `Numero offerte` = var_new_offers_number ,`Valore max offerta` = var_amount
        WHERE ID = var_object;
        
     -- cerco utente controfferta automatica 
        SELECT Utente , `Importo massimo automatico` 
        INTO var_user_controfferta, var_amount_controfferta
        FROM offerta  
        WHERE `oggetto in asta` = var_object
        AND `importo massimo automatico` is not null
        AND Utente <> var_user
        GROUP BY Utente, `Importo massimo automatico` 
        HAVING MAX(`Importo massimo automatico`) > var_controfferta 
	    ORDER BY `importo massimo automatico` DESC 
        LIMIT 1;
        
        IF var_amount_controfferta is not null AND var_amount_controfferta >= var_amount + 0.5 then 
		  
           INSERT INTO offerta( Utente, Importo, `Oggetto in asta`, `Data`, Ora, `importo massimo automatico`)
           VALUES (var_user_controfferta, var_amount + 0.5, var_object, current_date(), current_time());
           
           UPDATE `oggetto in asta`
		   SET `Numero offerte` = `Numero offerte` + 1 ,`Valore max offerta` = var_amount + 0.5
           WHERE ID = var_object;
        
        END IF;   
        
        COMMIT;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure make_offer
-- -----------------------------------------------------

DELIMITER $$
USE `aste`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `make_offer`(in var_user VARCHAR(16), in var_amount FLOAT, in var_object VARCHAR(10))
BEGIN	
		DECLARE var_offer_date DATE;
        DECLARE var_offer_time TIME;
        DECLARE var_card_expire_date VARCHAR(5);
        DECLARE var_curr_max_offer FLOAT;
        DECLARE var_object_type ENUM('in corso','venduto','non venduto');
        DECLARE var_offers_number INT;
        DECLARE var_new_offers_number INT;
        DECLARE var_finish_auction DATE;
        DECLARE var_start_auction DATETIME;
		DECLARE var_base_price FLOAT;
        -- CONTROFFERTA
		DECLARE var_user_controfferta VARCHAR(16);
        DECLARE var_amount_controfferta FLOAT;
	    DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN 
			  ROLLBACK;
              RESIGNAL;
	    END;
        
        SET TRANSACTION ISOLATION LEVEL SERIALIZABLE; 
        START TRANSACTION;
        
        SELECT `Base d'asta`, `Numero offerte`, `Valore max offerta`, `Termine asta`, `Inizio asta`, Tipo
        INTO   var_base_price, var_offers_number, var_curr_max_offer, var_finish_auction, var_start_auction, var_object_type
        FROM   `oggetto in asta`
        WHERE  	ID = var_object;
        
        IF var_amount % 0.5 <> 0 then
		   SIGNAL SQLSTATE '45010' SET MESSAGE_TEXT = "Offer amount not valid";
		END IF;  
        
        IF var_amount <= var_curr_max_offer then
		   SIGNAL SQLSTATE '45011' SET MESSAGE_TEXT = "Offer amount too low";
        END IF;
        
		IF var_offers_number = 0 then
		   IF var_amount < var_base_price then
			  SIGNAL SQLSTATE '45012' SET MESSAGE_TEXT = "Offer amount less than start price ";
		   END IF;
	    END IF;
        
        IF var_object_type <> 'in corso' then 
		    SIGNAL SQLSTATE '45013' SET MESSAGE_TEXT = "Auction finished";
        END IF;
        
        IF var_finish_auction < CURRENT_DATE() then 
            SIGNAL SQLSTATE '45013' SET MESSAGE_TEXT = "Auction finished";
		END IF;
         
		IF var_finish_auction = CURRENT_DATE() and TIME(var_start_auction) < CURRENT_TIME() then 
            SIGNAL SQLSTATE '45013' SET MESSAGE_TEXT = "Auction finished";
		END IF;
         
		SELECT Scadenza INTO var_card_expire_date
        FROM `carta di credito` JOIN Utente ON `carta di credito`.Numero = Utente.`Carta di Credito`
        WHERE CF = var_user;
	-- il pagamento avviene alla fine dell'asta, quindi è importante che la carta non scada prima della fine di essa 
        IF STR_TO_DATE(CONCAT('01-',var_card_expire_date), '%d-%m-%y') < var_finish_auction then 
		   SIGNAL SQLSTATE '45007' SET MESSAGE_TEXT = " Expired Credit Card ";
		END IF;
        
        SET var_offer_date = CURRENT_DATE();
        SET var_offer_time = CURRENT_TIME();
        
        SELECT `Numero offerte` INTO var_offers_number
        FROM   `oggetto in asta`
        WHERE  ID = var_object;
        
        SET var_new_offers_number = var_offers_number + 1;
	 -- inserimento offerta 
        INSERT INTO offerta( Utente, Importo, `Oggetto in asta`, `Data`, Ora )
        VALUES ( var_user, var_amount, var_object, var_offer_date, var_offer_time );
	 -- aggiorno numero offerte e max offerta
		UPDATE `oggetto in asta`
        SET `Numero offerte` = var_new_offers_number ,`Valore max offerta` = var_amount
        WHERE ID = var_object;
     -- cerco utente controfferta automatica 
        SELECT Utente , `Importo massimo automatico` 
        INTO var_user_controfferta, var_amount_controfferta
        FROM offerta  
        WHERE `oggetto in asta` = var_object
        AND `importo massimo automatico` is not null
        AND Utente <> var_user
        GROUP BY Utente, `Importo massimo automatico` 
        HAVING MAX(`Importo massimo automatico`)
	    ORDER BY `importo massimo automatico` DESC 
        LIMIT 1;
        
        IF var_amount_controfferta is not null AND var_amount_controfferta >= var_amount + 0.5 then 
		  
           INSERT INTO offerta( Utente, Importo, `Oggetto in asta`, `Data`, Ora, `importo massimo automatico`)
           VALUES (var_user_controfferta, var_amount + 0.5, var_object, current_date(), current_time(),var_amount_controfferta);
           
           UPDATE `oggetto in asta`
		   SET `Numero offerte` = `Numero offerte` + 1 ,`Valore max offerta` = var_amount + 0.5
           WHERE ID = var_object;
        
        END IF;   
        
        COMMIT;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure modify_category_procedure
-- -----------------------------------------------------

DELIMITER $$
USE `aste`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `modify_category_procedure`( in var_old_name VARCHAR(45), in var_new_name VARCHAR(45) )
BEGIN
		DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN 
			  ROLLBACK;
              RESIGNAL;
		END;
        
        SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;
        START TRANSACTION;
        
			UPDATE categoria 
            SET nome = var_new_name
            WHERE nome = var_old_name;
		 
            IF ROW_COUNT() = 0 THEN
				ROLLBACK;
				SIGNAL SQLSTATE '45070' SET MESSAGE_TEXT = 'Category does not exist';
			ELSE
				COMMIT;
			END IF;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure modify_parent_category_procedure
-- -----------------------------------------------------

DELIMITER $$
USE `aste`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `modify_parent_category_procedure`( in var_name VARCHAR(45), in var_parent VARCHAR(45) )
BEGIN
		DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN 
			  ROLLBACK;
              RESIGNAL;
		END;
        
        SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;
        START TRANSACTION;
        
			UPDATE categoria 
            SET categoriaGenitore = var_parent
            WHERE nome = var_name;
			
			IF ROW_COUNT() = 0 THEN
				ROLLBACK;
				SIGNAL SQLSTATE '45070' SET MESSAGE_TEXT = 'Category does not exist';
			ELSE
				COMMIT;
			END IF;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure show_categories
-- -----------------------------------------------------

DELIMITER $$
USE `aste`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `show_categories`()
BEGIN
		DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN 
			  ROLLBACK;
              RESIGNAL;
		END;
        
        SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
        START TRANSACTION;
        
        SELECT nome, categoriaGenitore 
        FROM categoria ;
        
        COMMIT;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure show_open_auction
-- -----------------------------------------------------

DELIMITER $$
USE `aste`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `show_open_auction`()
BEGIN
	  DECLARE EXIT HANDLER FOR SQLEXCEPTION 
      BEGIN 
		    ROLLBACK;
            RESIGNAL;
	  END;
      
      SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
      START TRANSACTION;
      
      SELECT ID, Descrizione, Stato, `Base d'asta`, Dimensioni, `Inizio asta`, `Termine asta`,`Numero offerte`,`Valore max offerta`,Categoria
      FROM  `oggetto in asta`
      WHERE  Tipo = 'in corso'
      AND    `Termine asta` > CURRENT_DATE()
			 UNION 
      SELECT ID, Descrizione, Stato, `Base d'asta`, Dimensioni, `Inizio asta`, `Termine asta`,`Numero offerte`,`Valore max offerta`,Categoria
      FROM  `oggetto in asta`
      WHERE  Tipo = 'in corso'
      AND    `Termine asta` = CURRENT_DATE()
      AND 	 `Inizio asta`- CURRENT_TIME > 0;
      
      COMMIT;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure user_registration_procedure
-- -----------------------------------------------------

DELIMITER $$
USE `aste`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `user_registration_procedure`(
			  in var_cf				VARCHAR(16),
			  in var_name 			VARCHAR(45),
			  in var_surname 		VARCHAR(45),
			  in var_birth_date 	DATE,
			  in var_birth_city	    VARCHAR(45),
			  in var_address 		VARCHAR(45),
			  in var_civico 		VARCHAR( 6),
			  in var_comune 		VARCHAR(45), 
			  in var_cap 			VARCHAR( 5),
			  in var_credit_card    VARCHAR(16),
			  in var_holder_name	VARCHAR(45),
			  in var_holder_surname VARCHAR(45),
			  in var_expire_date    VARCHAR( 5),
			  in var_cvv 	    	VARCHAR( 4),
			  in var_username 		VARCHAR(45),
			  in var_password 		VARCHAR(45)
											   )
BEGIN
		DECLARE EXIT HANDLER FOR SQLEXCEPTION
		BEGIN
				ROLLBACK;
				RESIGNAL;
		END;
	 -- Si effettuano i vari controlli per il corretto inserimento dei dati 
        SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
        START TRANSACTION;
        
         -- Controllo se il username esiste già nella tabella amministratore
		IF (SELECT COUNT(*) FROM amministratore WHERE username = var_username) > 0 OR (SELECT COUNT(*) FROM utente WHERE username = var_username) > 0 > 0 THEN
			SIGNAL SQLSTATE '45040' SET MESSAGE_TEXT = "Username already exists";
		END IF;
        
        IF(check_cf(var_cf) is false) then 
			SIGNAL SQLSTATE '45001' SET message_text = " Invalid Codice Fiscale ";
		END IF;
        
        IF(check_civico(var_civico) is false) then 
			SIGNAL SQLSTATE '45002' SET message_text = " Invalid House Number ";
		END IF;
        
		IF(check_cap(var_cap) is false) then 
			SIGNAL SQLSTATE '45003' SET message_text = " Invalid Postal Code ";
		END IF;
        
        IF(check_credit_card(var_credit_card) is false) then 
			SIGNAL SQLSTATE '45004' SET message_text = " Invalid Credit Card Number ";
        END IF;
        
		IF(check_cvv(var_cvv) is false) then 
			SIGNAL SQLSTATE '45005' SET message_text = " Invalid CVV ";
		END IF;
        
        IF(check_expire_date(var_expire_date) is false) then 
			SIGNAL SQLSTATE '45006' SET message_text = " Invalid Expire Date ";
        END IF;
        
		IF(check_valid_expire(var_expire_date) is false) then 
			SIGNAL SQLSTATE '45007' SET message_text = " Expired Credit Card ";
		END IF;
        
        INSERT INTO `Carta di Credito`( Numero, Nome, Cognome, Scadenza, CVV )
        VALUES ( var_credit_card, var_holder_name, var_holder_surname, var_expire_date, var_cvv );
        
        INSERT INTO Utente(CF, Nome, Cognome, `Data di Nascita`, `Città di nascita`, Via, Civico, Comune, CAP, `Carta di Credito`, Username, `Password`)
		VALUES(var_cf, var_name, var_surname, var_birth_date, var_birth_city, var_address, var_civico, var_comune, var_cap, var_credit_card, var_username, md5(var_password) );	
        
        COMMIT;
		
END$$

DELIMITER ;
USE `aste`;

DELIMITER $$
USE `aste`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `aste`.`check_category_level`
BEFORE INSERT ON `aste`.`categoria`
FOR EACH ROW
BEGIN
    DECLARE level INT DEFAULT 0;
    DECLARE parent_category_name VARCHAR(45);
    
    SET parent_category_name = NEW.categoriaGenitore;
    
    -- Ciclo per verificare il livello della gerarchia
    WHILE parent_category_name IS NOT NULL DO
        SET level = level + 1;
        
        -- Recupera il genitore della categoria corrente
        SELECT categoriaGenitore INTO parent_category_name
        FROM categoria
        WHERE nome = parent_category_name;
        
        -- Verifica se il livello supera il massimo consentito
        IF level > 2 THEN
            SIGNAL SQLSTATE '45020' SET MESSAGE_TEXT = 'Error: The category exceeds the maximum allowed hierarchy level (3 levels).';
        END IF;
    END WHILE;
END$$

USE `aste`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `aste`.`check_category_level_update`
BEFORE UPDATE ON `aste`.`categoria`
FOR EACH ROW
BEGIN
    DECLARE level INT DEFAULT 0;
    DECLARE parent_category_name VARCHAR(45);
    
    SET parent_category_name = NEW.categoriaGenitore;
    
    -- Ciclo per verificare il livello della gerarchia
    WHILE parent_category_name IS NOT NULL DO
        SET level = level + 1;
        
        -- Recupera il genitore della categoria corrente
        SELECT categoriaGenitore INTO parent_category_name
        FROM categoria
        WHERE nome = parent_category_name;
        
        -- Verifica se il livello supera il massimo consentito
        IF level > 2 THEN
            SIGNAL SQLSTATE '45020' SET MESSAGE_TEXT = 'Error: The category exceeds the maximum allowed hierarchy level (3 levels).';
        END IF;
    END WHILE;
END$$

USE `aste`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `aste`.`check_auction_duration`
BEFORE INSERT ON `aste`.`oggetto in asta`
FOR EACH ROW
BEGIN 
	IF NEW.`durata asta` < 1 OR NEW.`durata asta` > 7 THEN 
		SIGNAL SQLSTATE '45030'
        SET MESSAGE_TEXT = 'The duration of the auction must be between 1 and 7 days';
	END IF;
    IF DATEDIFF(NEW.`termine asta`,NEW.`inizio asta`) != NEW.`durata asta` THEN 
		SIGNAL SQLSTATE '45031'
        SET MESSAGE_TEXT = ' The duration of the auction does not match with start and finish date';
	END IF;
END$$


DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
