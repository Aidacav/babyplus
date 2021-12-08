DROP SCHEMA IF EXISTS BABYPLUS;
CREATE SCHEMA IF NOT EXISTS BABYPLUS;

CREATE TABLE IF NOT EXISTS BABYPLUS.ROL
(
	ID INT AUTO_INCREMENT PRIMARY KEY,
        DESCRIPCION VARCHAR(50) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO BABYPLUS.ROL (ID,DESCRIPCION) VALUES
(NULL, 'ADMIN'),
(NULL, 'CLIENTE'),
(NULL, 'PROVEEDOR');

CREATE TABLE IF NOT EXISTS BABYPLUS.USUARIO
(
	ID INT AUTO_INCREMENT PRIMARY KEY,
        USUARIO VARCHAR(50) NOT NULL,
        PASSWORD VARCHAR(50) NOT NULL,
        FECHA_ALTA DATE NOT NULL,
        ACTIVO TINYINT(1) NOT NULL DEFAULT 0,
        ROL INT NOT NULL,
        IDIOMA VARCHAR(2) NOT NULL DEFAULT 'ES',
        FOREIGN KEY (ROL) REFERENCES ROL(ID)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS BABYPLUS.CLIENTE
(
	USUARIO INT NOT NULL,
        NOMBRE VARCHAR(50) NOT NULL,
        APELLIDOS VARCHAR(100) NOT NULL,
        FECHA_NACIMIENTO DATE NOT NULL,
        DOMICILIO VARCHAR(200) NOT NULL,
        LOCALIDAD VARCHAR(100) NOT NULL,
        CP INT(5) NOT NULL,
        PRIMARY KEY (USUARIO),
        FOREIGN KEY (USUARIO) REFERENCES USUARIO(ID)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS BABYPLUS.PACIENTE
(
        ID INT AUTO_INCREMENT PRIMARY KEY,
	CLIENTE INT NOT NULL,
        NOMBRE VARCHAR(50) NOT NULL,
        FECHA_NACIMIENTO DATE NOT NULL,
        OBSERVACIONES VARCHAR(300),
        FOREIGN KEY (CLIENTE) REFERENCES CLIENTE(USUARIO)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS BABYPLUS.PROVEEDOR
(
	USUARIO INT NOT NULL,
        RAZON_SOCIAL VARCHAR(50) NOT NULL,
        CIF VARCHAR(9) NOT NULL,
        DIRECCION VARCHAR(200) NOT NULL,
        LOCALIDAD VARCHAR(100) NOT NULL,
        CP INT(5) NOT NULL,
        LOGO LONGBLOB NOT NULL,
        RESPONSABLE VARCHAR(100),
        PRIMARY KEY (USUARIO),
        FOREIGN KEY (USUARIO) REFERENCES USUARIO(ID)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS BABYPLUS.SERVICIO
(
	ID INT AUTO_INCREMENT PRIMARY KEY,
        DESCRIPCION VARCHAR(150) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO BABYPLUS.SERVICIO (ID,DESCRIPCION) VALUES
(NULL, 'PODOLOGIA'),
(NULL, 'TRAUMATOLOGIA'),
(NULL, 'DERMATOLOGIA'),
(NULL, 'ALERGOLOGIA'),
(NULL, 'NUTRICION'),
(NULL, 'ASESORIA_LACTANCIA'),
(NULL, 'MATRONAS'),
(NULL, 'DOULAS'),
(NULL, 'PSICOLOGIA'),
(NULL, 'LOGOPEDIA'),
(NULL, 'OCULISTA'),
(NULL, 'OTORRINOLARINGOLOGIA'),
(NULL, 'FISIOTERAPIA'),
(NULL, 'DENTISTA');

CREATE TABLE IF NOT EXISTS BABYPLUS.PROVEEDOR_SERVICIO
(
        ID INT AUTO_INCREMENT PRIMARY KEY,
	PROVEEDOR INT NOT NULL,
        SERVICIO INT NOT NULL,
        PRECIO INT NOT NULL,
        DESCRIPCION TINYTEXT DEFAULT '',
        FOREIGN KEY (PROVEEDOR) REFERENCES PROVEEDOR(USUARIO),
        FOREIGN KEY (SERVICIO) REFERENCES SERVICIO(ID)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS BABYPLUS.SUBSCRIPCION
(
	ID INT AUTO_INCREMENT PRIMARY KEY,
        NOMBRE VARCHAR(20) NOT NULL,
        PRECIO INT NOT NULL,
        DURACION_DIAS INT NOT NULL DEFAULT 365,
        DESCRIPCION VARCHAR(100) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO BABYPLUS.SUBSCRIPCION (ID, NOMBRE, PRECIO, DURACION_DIAS, DESCRIPCION) VALUES
(NULL, 'ANUAL', 15, 365, 'Subscripción Anual'),
(NULL, 'MENSUAL', 2, 30, 'Subscripción Mensual');

CREATE TABLE IF NOT EXISTS BABYPLUS.CLIENTE_SUBSCRIPCION
(
	ID INT AUTO_INCREMENT PRIMARY KEY,
	CLIENTE INT NOT NULL,
	SUBSCRIPCION INT NOT NULL,
	FECHA_INICIO DATE NOT NULL,
	FECHA_FIN DATE NOT NULL,
	FOREIGN KEY (CLIENTE) REFERENCES CLIENTE(USUARIO),
	FOREIGN KEY (SUBSCRIPCION) REFERENCES SUBSCRIPCION(ID)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS BABYPLUS.ESTADO_SOLICITUD
(
	NOMBRE VARCHAR(20),
        PRIMARY KEY (NOMBRE)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO BABYPLUS.ESTADO_SOLICITUD (NOMBRE) VALUES
('ENVIADA'),
('ACEPTADA'),
('RECHAZADA');

CREATE TABLE IF NOT EXISTS BABYPLUS.SOLICITUD
(
	ID INT AUTO_INCREMENT PRIMARY KEY,
	CLIENTE INT NOT NULL,
        PACIENTE INT NOT NULL,
        PROVEEDOR INT NOT NULL,
        SERVICIO INT NOT NULL,
        FECHA DATE NOT NULL,
        ESTADO VARCHAR(20) DEFAULT 'ENVIADA',
        NOTAS TINYTEXT DEFAULT '',
	FOREIGN KEY (CLIENTE) REFERENCES CLIENTE(USUARIO),
	FOREIGN KEY (PACIENTE) REFERENCES PACIENTE(ID),
	FOREIGN KEY (PROVEEDOR) REFERENCES PROVEEDOR(USUARIO),
        FOREIGN KEY (SERVICIO) REFERENCES PROVEEDOR_SERVICIO(ID),
        FOREIGN KEY (ESTADO) REFERENCES ESTADO_SOLICITUD(NOMBRE)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS BABYPLUS.ESTADO_CITA
(
	NOMBRE VARCHAR(20),
        PRIMARY KEY (NOMBRE)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO BABYPLUS.ESTADO_CITA (NOMBRE) VALUES
('PENDIENTE'),
('REALIZADA'),
('NO_REALIZADA'),
('FINALIZADA');

CREATE TABLE IF NOT EXISTS BABYPLUS.CITA
(
	ID INT AUTO_INCREMENT PRIMARY KEY,
	SOLICITUD INT NOT NULL,
        FECHA DATE NOT NULL,
        ESTADO VARCHAR(20) NOT NULL,
        NOTAS TINYTEXT DEFAULT '',
	FOREIGN KEY (SOLICITUD) REFERENCES SOLICITUD(ID),
        FOREIGN KEY (ESTADO) REFERENCES ESTADO_CITA(NOMBRE)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS BABYPLUS.MENSAJE
(
	ID INT AUTO_INCREMENT PRIMARY KEY,
        CLIENTE INT NOT NULL,
        PROVEEDOR INT NOT NULL,
        ORIGEN INT NOT NULL,
        FECHA DATETIME DEFAULT CURRENT_TIMESTAMP,
        MENSAJE TINYTEXT DEFAULT '',
	FOREIGN KEY (CLIENTE) REFERENCES CLIENTE(USUARIO),
        FOREIGN KEY (PROVEEDOR) REFERENCES PROVEEDOR(USUARIO)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS BABYPLUS.VALORACION
(
        CITA INT,
        FECHA DATE DEFAULT CURRENT_TIMESTAMP,
        CHUPETES INT NOT NULL,
        MENSAJE TINYTEXT DEFAULT '',
        PRIMARY KEY (CITA),
	FOREIGN KEY (CITA) REFERENCES CITA(ID)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS BABYPLUS.POST
(
        ID INT AUTO_INCREMENT PRIMARY KEY,
        FECHA_CREACION DATE DEFAULT CURRENT_TIMESTAMP,
        FECHA_EXPIRACION DATE,
        POST TINYTEXT DEFAULT ''
)ENGINE=InnoDB DEFAULT CHARSET=latin1;
