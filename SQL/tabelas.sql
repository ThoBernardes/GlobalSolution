
CREATE TABLE Regiao (
    id_Regiao INTEGER PRIMARY KEY,
    nome VARCHAR(255)
);


CREATE TABLE Mercado (
    id_Mercado INTEGER PRIMARY KEY,
    nome VARCHAR(255),
    endereco VARCHAR(255),
    id_Regiao INTEGER,
    FOREIGN KEY (id_Regiao) REFERENCES Regiao(id_Regiao)
);


CREATE TABLE Unidade (
    id_Unidade INTEGER PRIMARY KEY,
    nome VARCHAR(255),
    endereco VARCHAR(255),
    publico VARCHAR(255),
    id_Regiao INTEGER,
    FOREIGN KEY (id_Regiao) REFERENCES Regiao(id_Regiao)
);


CREATE TABLE Produto (
    id_Produto INTEGER PRIMARY KEY,
    nome VARCHAR(255),
    id_Mercado INTEGER,
    lote INTEGER,
    quantidade INTEGER,
    validade DATE,
    FOREIGN KEY (id_Mercado) REFERENCES Mercado(id_Mercado)
);
