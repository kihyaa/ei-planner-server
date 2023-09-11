package com.kihyaa.Eiplanner.controller;

import com.kihyaa.Eiplanner.dto.request.DeleteHistoryRequest;
import com.kihyaa.Eiplanner.dto.request.GetHistoryRequest;
import com.kihyaa.Eiplanner.dto.response.ApiResponse;
import com.kihyaa.Eiplanner.dto.response.GetHistoryResponse;
import com.kihyaa.Eiplanner.service.HistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/history")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping
    public ResponseEntity<GetHistoryResponse> getHistory(@RequestBody @Valid GetHistoryRequest request,
                                                        @PageableDefault(page=0, size=5) Pageable pageable){

        GetHistoryResponse historyList = historyService.getHistory(request.getUser_pk(), pageable);

        return ResponseEntity.ok(historyList);
    }

    @DeleteMapping("/{task_id}")
    public ResponseEntity<ApiResponse> deleteOneHistory(@PathVariable(value = "task_id") Long taskId,
                                                        @RequestBody @Valid DeleteHistoryRequest request){
        boolean result = historyService.deleteOneHistory(taskId, request.getUser_pk());

        ApiResponse response = new ApiResponse(HttpStatus.OK.value(), LocalDateTime.now(), "히스토리 내 일정 삭제 성공");

        // Todo : 실패시 상태코드 400 날려야 하는데 Service 단에서 에러 터지게 해놨음. 그거 캐치해서 상태코드 넘겨줘야할듯

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/clean")
    public ResponseEntity<ApiResponse> deleteAllHistory(@RequestBody @Valid DeleteHistoryRequest request){
        boolean result = historyService.deleteAllHistory(request.getUser_pk());

        ApiResponse response = new ApiResponse(HttpStatus.OK.value(), LocalDateTime.now(), "히스토리 비우기 성공");

        return ResponseEntity.ok(response);
    }



}