package com.auth.spotify.controller;

import com.auth.spotify.model.Playlist;
import com.auth.spotify.service.PlaylistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @PostMapping
    public ResponseEntity<PlaylistDTO> create(@RequestBody CreatePlaylistRequest request) {
        Playlist playlist = playlistService.createPlaylist(request.name());
        return ResponseEntity.ok(mapToDTO(playlist));
    }

    @PostMapping("/{id}/songs")
    public ResponseEntity<Void> addSong(@PathVariable Long id, @RequestBody SongRequest request) {
        playlistService.addSong(id, request.title(), request.artist(), request.externalId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaylistDTO> getById(@PathVariable Long id) {
        Playlist playlist = playlistService.getPlaylist(id);
        return ResponseEntity.ok(mapToDTO(playlist));
    }

    private PlaylistDTO mapToDTO(Playlist playlist) {
        List<SongDTO> songDTOs = playlist.getSongs().stream()
                .map(s -> new SongDTO(s.getTitle(), s.getArtist(), s.getExternalId()))
                .collect(Collectors.toList());
        return new PlaylistDTO(playlist.getId(), playlist.getName(), songDTOs);
    }

    public record CreatePlaylistRequest(String name) {}
    public record SongRequest(String title, String artist, String externalId) {}

    public record SongDTO(String title, String artist, String externalId) {}
    public record PlaylistDTO(Long id, String name, List<SongDTO> songs) {}
}