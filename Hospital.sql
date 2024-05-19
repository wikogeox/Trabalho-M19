create database hospital;

use hospital;

create table paciente(
	id int(5) primary key auto_increment,
    nome varchar(80) not null,
    idade int(5) not null,
    morada varchar(120),
    telefone varchar(13),
    email varchar(255),
    numUtente int(9)
    );
    
create table especialidade(
	id int(5) primary key auto_increment,
    nome varchar(30) not null
    );
    
create table medico(
	id int(5) primary key auto_increment,
    nome varchar(80) not null,
    idade int(5) not null,
    idEspecialidade int(5) not null,
    foreign key (idEspecialidade) references especialidade(id)
	);
    
create table consulta(
	idMedico int(5),
    idPaciente int(5),
    dataConsulta date,
    horaConsulta time,
    primary key (idMedico, idPaciente, dataConsulta, horaConsulta),
    foreign key (idMedico) references medico(id),
    foreign key (idPaciente) references paciente(id)
    );
    
    
INSERT INTO paciente (nome, idade, morada, telefone, email, numUtente) VALUES
('Simão Costa', '41', 'Lisboa', '+351987654657', 'Simao123@gmail.com', '583758368');

INSERT INTO paciente (nome, idade, morada, telefone, email, numUtente) VALUES
('Mariana Teixeira', '22', 'Lisboa', '+351912334356', 'Mariana@gmail.com', '345543748');

INSERT INTO paciente (nome, idade, morada, telefone, email, numUtente) VALUES
('Mariano Sousa', '55', 'Lisboa', '+351967235553', 'MSousa@gmail.com', '837583749');

INSERT INTO paciente (nome, idade, morada, telefone, email, numUtente) VALUES
('Miguel Furtado', '31', 'Lisboa', '+351943689094', 'Miguel@homail.com', '453835849');

INSERT INTO paciente (nome, idade, morada, telefone, email, numUtente) VALUES
('Fabiana Ramos', '22',  'Lisboa', '+351910982653', 'FabianaRamos53@gmail.com', '444758372');	


INSERT INTO especialidade (nome) VALUES ('pediatria');	
INSERT INTO especialidade (nome) VALUES ('obstetrícia');	
INSERT INTO especialidade (nome) VALUES ('cardiologia');	
INSERT INTO especialidade (nome) VALUES ('dermatologia');	
INSERT INTO especialidade (nome) VALUES ('otorrinolaringologia');	
INSERT INTO especialidade (nome) VALUES ('neurologia');	
INSERT INTO especialidade (nome) VALUES ('clinica geral');	
INSERT INTO especialidade (nome) VALUES ('nutrição');	
INSERT INTO especialidade (nome) VALUES ('oncologia');	
INSERT INTO especialidade (nome) VALUES ('oftamologia');	
INSERT INTO especialidade (nome) VALUES ('radiologia');	
INSERT INTO especialidade (nome) VALUES ('urologia');	
INSERT INTO especialidade (nome) VALUES ('psiquiatria');	
INSERT INTO especialidade (nome) VALUES ('ortopedia');	



INSERT INTO medico (nome, idade, idEspecialidade) VALUES
('João Silva', '22', '1');	

INSERT INTO medico (nome, idade, idEspecialidade) VALUES
('Joana Ferreira', '27', '2');	

INSERT INTO medico (nome, idade, idEspecialidade) VALUES
('Mario Valente', '32', '3');	

INSERT INTO medico (nome, idade, idEspecialidade) VALUES
('António Costa', '45', '4');	

INSERT INTO medico (nome, idade, idEspecialidade) VALUES
('Maria Rocha', '50','5');	

INSERT INTO medico (nome, idade, idEspecialidade) VALUES
('Andreia Cunha', '37', '6');	

INSERT INTO medico (nome, idade, idEspecialidade) VALUES
('João Santos', '44', '7');	

INSERT INTO medico (nome, idade, idEspecialidade) VALUES
('André Reis', '22', '8');	

INSERT INTO medico (nome, idade, idEspecialidade) VALUES
('Helder Almeida', '41', '9');	

INSERT INTO medico (nome, idade, idEspecialidade) VALUES
('Manoela Pires', '43', '10');	

INSERT INTO medico (nome, idade, idEspecialidade) VALUES
('Érica Ramos', '29', '11');	

INSERT INTO medico (nome, idade, idEspecialidade) VALUES
('Mario Machado', '37', '12');	

INSERT INTO medico (nome, idade, idEspecialidade) VALUES
('Joana Sousa', '28', '13');	

INSERT INTO medico (nome, idade, idEspecialidade) VALUES
('Maria Martins ', '30', '14');	



INSERT INTO consulta (idMedico, idPaciente, dataConsulta, horaConsulta) VALUES
('1', '1', '2024-05-20', '14:00');	

INSERT INTO consulta (idMedico, idPaciente, dataConsulta, horaConsulta) VALUES
('3', '1', '2024-05-22', '14:00');

INSERT INTO consulta (idMedico, idPaciente, dataConsulta, horaConsulta) VALUES
('2', '2', '2024-05-20', '14:00');

INSERT INTO consulta (idMedico, idPaciente, dataConsulta, horaConsulta) VALUES
('14', '5', '2024-06-01', '10:00');

INSERT INTO consulta (idMedico, idPaciente, dataConsulta, horaConsulta) VALUES
('13', '4', '2024-05-25', '11:00');
