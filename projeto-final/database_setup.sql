-- Script para criar as tabelas necessárias para o projeto de Lista de Tarefas

-- Criar tabela de usuários (se não existir)
CREATE TABLE IF NOT EXISTS usuarios (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    usuario VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(50) NOT NULL,
    admin BOOLEAN DEFAULT FALSE
);

-- Criar tabela de tarefas
CREATE TABLE IF NOT EXISTS tarefas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(150) NOT NULL,
    descricao TEXT,
    data_criacao DATETIME DEFAULT CURRENT_TIMESTAMP,
    data_vencimento DATE NOT NULL,
    status VARCHAR(20) DEFAULT 'Pendente',
    prioridade VARCHAR(20) DEFAULT 'Normal',
    id_usuario INT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
);

-- Exemplo de dados iniciais (opcional)
INSERT INTO usuarios (nome, usuario, senha, admin) VALUES 
('Admin', 'admin', 'admin', TRUE),
('João Silva', 'joao', '123456', FALSE),
('Maria Santos', 'maria', '123456', FALSE);

INSERT INTO tarefas (titulo, descricao, data_vencimento, status, prioridade, id_usuario) VALUES 
('Estudar Java', 'Revisar conceitos de OOP', '2024-12-31', 'Pendente', 'Alta', 2),
('Fazer compras', 'Compras do mês', '2024-04-15', 'Em Progresso', 'Normal', 3),
('Projeto Final', 'Completar projeto de lista de tarefas', '2024-12-20', 'Pendente', 'Alta', 2);
