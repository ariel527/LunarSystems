# Lunar Systems – Sistema de Controle de Missões Lunares

## Introdução

O Lunar Systems é um sistema em Java desenvolvido para gerenciar astronautas, naves e missões lunares. Ele foi criado como um projeto acadêmico para demonstrar o uso de Programação Orientada a Objetos (POO), arquivos binários, organização em camadas (MVC) e estruturação de projetos utilizando Maven.

O sistema funciona totalmente em modo console, permitindo que o usuário cadastre elementos, liste, remova e relacione astronautas com missões, além de registrar resultados.

---

## Objetivo do Sistema

O objetivo do Lunar Systems é fornecer um ambiente que simule a gestão de missões lunares. O usuário pode:

- Cadastrar astronautas.
- Cadastrar naves.
- Criar missões.
- Adicionar astronautas às missões.
- Registrar o resultado de missões.
- Persistir dados em arquivos binários automaticamente.

---

## Arquitetura do Projeto

A aplicação segue o padrão MVC e é dividida da seguinte forma:

src/
└── main/
├── java/
│ └── br.lunarsystems/
│ ├── service/ → Regras de negócio
│ ├── model/ → Classes principais (Astronauta, Nave, Missão)
│ └── view/ → Interface de console (App.java)
└── resources/


Os dados são armazenados na pasta `data/`, utilizando arquivos binários:

- astronautas.bin
- naves.bin
- missoes.bin

---

## Tecnologias Utilizadas

- Java 17+
- Maven
- Persistência com ObjectOutputStream / ObjectInputStream
- Arquitetura MVC
- SLF4J (logger padrão do Maven)

---

## Funcionalidades

### Astronautas
- Cadastrar astronauta
- Listar astronautas
- Remover astronauta

### Naves
- Cadastrar nave
- Listar naves
- Remover nave

### Missões
- Cadastrar missão
- Listar missões
- Remover missão
- Adicionar astronauta a missão
- Registrar resultado da missão

---

## Como Executar o Projeto

### 1. Pré-requisitos

Instalar:

- Java 17 ou superior  
- Apache Maven  
- Git (opcional, caso vá clonar o repositório)  

---

### 2. Clonar o repositório

git clone https://github.com/SEU-USUARIO/LunarSystems.git
cd LunarSystems

### 3. Criar diretório de dados

mkdir data

### 4. Executar o sistema

mvn exec:java -Dexec.mainClass="br.lunarsystems.view.App"

### Menu principal

--- Lunar Systems ---
1. Cadastrar Astronauta
2. Listar Astronautas
3. Remover Astronauta
4. Cadastrar Nave
5. Listar Naves
6. Remover Nave
7. Cadastrar Missão
8. Listar Missões
9. Remover Missão
10. Adicionar Astronauta a Missão
11. Registrar Resultado da Missão
0. Sair

### Persistencia de dados

Todos os registros são automaticamente salvos na pasta data/.

Exemplos de arquivos:

data/astronautas.bin

data/naves.bin

data/missoes.bin

A persistência é automática e os dados são mantidos mesmo após fechar o programa.

### Conclusao

O Lunar Systems é um sistema acadêmico que demonstra os princípios de POO, modularização, persistência e arquitetura limpa. A aplicação é simples, funcional e cumpre todos os requisitos de gerenciamento de entidades e missões.



