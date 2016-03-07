(function($, hljs, document) {
  var _rankingTimer = 30 * 1000;
  var _modalTimer   = 5 * 1000;

  var rankingListWatcher;

  function startGetRankingList() {
    rankingListWatcher = setInterval(getRankingList, _rankingTimer);
  }

  function stopGetRankingList() {
    clearInterval(rankingListWatcher);
  }

  function getRankingList() {
    $.ajax({
      url: "/results",
      method: "GET"
    }).done(function (data) {
      _generateRankingTableElement($('#group-A tbody'), data['A']);
      _generateRankingTableElement($('#group-B tbody'), data['B']);
    }).fail(function (jqXHR, textStatus) {
      console.log('서버 접속 에러 발생!!');
      stopGetRankingList();
    });
  }

  function _generateRankingTableElement($el, rankingList) {
    $el.empty();
    for (var i = 0, ranking; ranking = rankingList[i]; i++) {
      $el.append('<tr><td>' + ranking['ranking'] + '</td>' +
                     '<td>' + ranking['name'] + '</td>' +
                     '<td>' + ranking['timeMillis'] + ' ms</td>' +
                     '<td>' +
                          '<a href="#">[소스코드]</a>' +
                          '<pre class="hide">' + ranking['source'] + '</pre>' +
                     '</td>' +
        '</tr>');
    }

    $el.find('a').on('click', function() {
      $('#result-ranking').text('');
      $('#result-name').text('');
      $('#result-time').text('');
      $('#result-source').text('');

      var $tdEls = $(this).parent().prevAll();

      console.log($tdEls);

      var ranking = $tdEls.eq(2).text();
      var name = $tdEls.eq(1).text();
      var time = $tdEls.eq(0).text();

      $('#result-ranking').text(ranking);
      $('#result-name').text(name);
      $('#result-time').text(time);
      $('#result-source').text($(this).next().text());

      $('#resultModal').modal('show');

      hljs.highlightBlock($('#result-source').parent('pre').get(0));
    });
  }

  function getMemberList() {
    $.ajax({
      url: "/members",
      method: "GET"
    }).done(function (data) {
      console.log('member : ' + JSON.stringify(data));
      _generateMemberSelectElement($('#email'), data);
    }).fail(function (jqXHR, textStatus) {
      console.log('서버 접속 에러 발생!!');
    });
  }

  function _generateMemberSelectElement($el, memberList) {
    $el.empty();
    $el.append('<option value="">선택</option>');
    for (var i = 0, member; member = memberList[i]; i++) {
      $el.append('<option value="' + member['email'] + '">' + member['name'] + '</option>');
    }
  }

  function sendAnswer(questionId, email, source) {
    $('#progress-bar').removeClass('hide');
    $('#register').attr('disabled', true);

    $.ajax({
      url: "/answers",
      method: "POST",
      data: {
        questionId: questionId,
        email: email,
        source: source
      }
    }).done(function (data) {
      console.log('result : ' + JSON.stringify(data));

      $('#progress-bar').addClass('hide');
      if (data['hasError']) {
        showResult('컴파일 또는 실행 중 에러가 발생하였습니다.', '<pre class="error-output">' +  data['errorOutput'] + '</pre>');
        $('#register').removeAttr('disabled');
      } else if (!data['answer']) {
        showResult('죄송합니다. 정답이 아님니다.', '<pre class="normal-output">' +  data['normalOutput'] + '</pre>');
        $('#register').removeAttr('disabled');
      } else {
        showResult('정상적으로 처리되었습니다. 실행 시간은 <strong>' + data['timeMillis'] + 'ms</strong> 입니다.<br/> 3초 후 자동으로 창이 닫힙니다.')

        setTimeout("$('#registerModal').modal('hide');", _modalTimer);

        getRankingList();
      }

    }).fail(function (jqXHR, textStatus) {
      $('#progress-bar').addClass('hide');
      closeModalOnError('서버와 연결 중 에러가 발생하였습니다. 나중에 다시 시도하십시요.');
    });
  }

  function closeModalOnError(message) {
    showResult(message + '<br/> 3초 후 자동으로 창이 닫힙니다.');
    setTimeout("$('#registerModal').modal('hide');", _modalTimer);
  }

  function showResult(message, errorMessage) {
    $('#alert-box').removeClass('hide');

    $('#alert-text').html(message);
    if (errorMessage != undefined) {
      $('#error-text').removeClass('hide');
      $('#error-text').html(errorMessage);
    }
  }

  function initResult() {
    $('#progress-bar').addClass('hide');
    $('#alert-box').addClass('hide');
    $('#error-text').addClass('hide');

    $('#alert-text').html('');
    $('#error-text').html('');

    $('#register').removeAttr('disabled');
  }

  $(document).ready(function () {
    var classValidationRep = /public\s+class\s+Main\s*\{/;
    var mainValidationRep = /public\s+static\s+void\s+main\s*\(/;

    getMemberList();
    getRankingList();

    // 등록 모달창 초기화
    $('#registerModal').on('shown.bs.modal', function () {
      console.log('#registerModal.shown.bs.modal');

      $('#source').val('');

      initResult();
    });

    // 소스코드만 수정해서 계속해서 재전송 가능하게
    $('#source').on('keyup', function () {
      console.log('#source.keyup');

      initResult();
    });

    $('#email').on('change', function () {
      console.log('#email.change');

      initResult();
    });

    $('#refresh').on('click', function () {
      console.log('#refresh.click');

      $(this).toggleClass('active');

      if ($(this).hasClass('active')) {
        startGetRankingList();
        $(this).html('<span class="glyphicon glyphicon-refresh" aria-hidden="true"></span> 새로고침 중');
        $(this).attr('title', '결과를 30초마다 새로고침 중입니다.');
      } else {
        stopGetRankingList();
        $(this).html('<span class="glyphicon glyphicon-refresh" aria-hidden="true"></span> 새로고침 중지');
        $(this).attr('title', '결과 새로고침이 중지되었습니다.')
      }
      $(this)[0].blur();
    });


    $('#register').on('click', function () {
      console.log('#register.click');

      if ($('#email option').size() <= 1) {
        closeModalOnError('서버와 연결 중 에러가 발생하였습니다. 나중에 다시 시도하십시요.');
        return;
      }

      if ($('#email').val() == '') {
        showResult('<strong>이름</strong>을 선택하십시요.');
        return;
      }
      if ($('#source').val() == '') {
        showResult('<strong>소스코드</strong>를 입력하십시요.');
        return;
      }
      if ($('#source').val().indexOf('package') > -1) {
        showResult('<strong>패키지</strong>를 지정하면 안됩니다.');
        return;
      }
      if (!classValidationRep.test($('#source').val())) {
        showResult('클래스명이 <strong>Main</strong>이어야 합니다.');
        return;
      }
      if (!mainValidationRep.test($('#source').val())) {
        showResult('<strong>main</strong> 메서드가 있어야 합니다.');
        return;
      }

      sendAnswer(2, $('#email').val(), $('#source').val());
    });

  });
})(jQuery, hljs, document);

