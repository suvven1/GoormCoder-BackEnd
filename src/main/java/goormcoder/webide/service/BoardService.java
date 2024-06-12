package goormcoder.webide.service;

import goormcoder.webide.common.dto.ErrorMessage;
import goormcoder.webide.domain.Board;
import goormcoder.webide.domain.Member;
import goormcoder.webide.domain.Question;
import goormcoder.webide.dto.request.BoardCreateDto;
import goormcoder.webide.exception.NotFoundException;
import goormcoder.webide.repository.BoardRepository;
import goormcoder.webide.repository.MemberRepository;
import goormcoder.webide.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final QuestionRepository questionRepository;

    // 게시글 생성
    @Transactional
    public void createBoard(BoardCreateDto boardCreateDto) {
        Member member = memberRepository.findByIdOrThrow(boardCreateDto.memberId());

        Question question = null;
        if(boardCreateDto.questionNum() != null) {
            question = questionRepository.findByQuestionNum(boardCreateDto.questionNum())
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.QUESTION_NOT_FOUND));
        }

        boardRepository.save(Board.of(boardCreateDto, member, question));
    }
}
