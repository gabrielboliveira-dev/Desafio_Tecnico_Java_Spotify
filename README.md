# Desafio Técnico: Spotify - Gerenciador de Playlists 🎵

## 📝 Contexto
Backend para gerenciamento de bibliotecas de música. O núcleo do desafio é modelar o relacionamento entre **Playlists** e **Músicas**, permitindo que uma mesma música pertença a múltiplas listas sem duplicação de dados, e simular a integração com metadados externos (título, artista, duração).

## 🚀 Requisitos Funcionais
1.  **Criar Playlist:** O usuário cria uma lista vazia com um nome (ex: "Viagem 2024").
2.  **Adicionar Música:**
    * O usuário envia os dados da música (Título, Artista, ID externo).
    * O sistema verifica se essa música já existe no banco local.
    * Se existir, reaproveita. Se não, salva.
    * Cria o vínculo (Link) entre a Playlist e a Música.
3.  **Listar Playlist:** Retorna a playlist com todas as suas músicas.
4.  **Remover Música:** Remove o vínculo da playlist, mas **não** apaga a música do banco (pois ela pode estar na playlist de outra pessoa).

## 🧠 Conceitos-Chave & Arquitetura
* **Many-to-Many Relationship:** Uso da anotação `@ManyToMany` do JPA.
* **Join Table:** Entendimento de como o banco cria uma tabela intermediária oculta para gerenciar os vínculos.
* **Data Integrity:** Garantir que apagar uma playlist não apague as músicas (Cascade Type).
* **DTO Pattern:** Obrigatório para evitar recursão infinita no JSON (Playlist tem Música que tem Playlist que tem Música...).

## 🛠️ Tecnologias
* Java 17 / Spring Boot 3
* Spring Data JPA (Hibernate)
* H2 Database

## 🔌 Endpoints

### `POST /playlists`
Cria uma nova playlist.
```json
{ "name": "Coding Focus" }
