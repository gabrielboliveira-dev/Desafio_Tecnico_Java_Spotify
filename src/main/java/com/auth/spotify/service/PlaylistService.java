package com.auth.spotify.service;

import com.auth.spotify.model.Playlist;
import com.auth.spotify.model.Song;
import com.auth.spotify.repository.PlaylistRepository;
import com.auth.spotify.repository.SongRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;

    public PlaylistService(PlaylistRepository playlistRepository, SongRepository songRepository) {
        this.playlistRepository = playlistRepository;
        this.songRepository = songRepository;
    }

    public Playlist createPlaylist(String name) {
        return playlistRepository.save(new Playlist(name));
    }

    public Playlist getPlaylist(Long id) {
        return playlistRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Playlist não encontrada"));
    }

    @Transactional
    public void addSong(Long playlistId, String title, String artist, String externalId) {
        Playlist playlist = getPlaylist(playlistId);
        Song song = songRepository.findByExternalId(externalId)
                .orElseGet(() -> {
                    return new Song(title, artist, externalId);
                });
        playlist.addSong(song);
        playlistRepository.save(playlist);
    }
}