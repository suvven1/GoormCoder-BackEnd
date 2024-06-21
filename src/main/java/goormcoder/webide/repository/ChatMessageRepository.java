package goormcoder.webide.repository;

import goormcoder.webide.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    
    @Query("SELECT cm FROM ChatMessage cm WHERE cm.chatRoom.id = :chatRoomId ORDER BY cm.createdAt")
    List<ChatMessage> findMessageByChatRoomId(@Param("chatRoomId") Long chatRoomId);

    @Query("SELECT cm FROM ChatMessage cm WHERE cm.chatRoom.id = :chatRoomId ORDER BY cm.createdAt DESC")
    List<ChatMessage> findLastMessageByChatRoomId(@Param("chatRoomId") Long chatRoomId);

}
