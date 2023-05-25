require: slotfilling/slotFilling.sc
  module = sys.zb-common
patterns:
    $hello = (салют|привет*|здравствуй*|здаров*|добрый (день|вечер)|хай)
    $bye = (пока|досвид*)
    $thanks = (*пасиб*|thank)

theme: /

    state: Start
        q!: $regex</start>
        a: Здравствуйте! Чем я могу помочь?

    state: Hello
        q!: * $hello *
        a: Тоже рад вас видеть! Могу я вам помочь?
    
    state: Tikets return
        q!: * (~вернуть|~возврат [(~деньги|~билет)]) *
        a: Укажите причину возврата
        state: Film screening cancellation
            q: * [~сеанс|~показ] * (~отмена|~отменен|~отменить) *
            go!: /Payment method
    
    state: Payment method
        a: Как вы приобрели билет: в кассе кинотеатра или online?
        state: Online
            q: * (~online|~онлайн |~сайт |~ормор)
    
    
    state: Bye
        q!: * $bye *
        a: До новых встреч, если ничего больше не интересует
        state: no
            q: * [гггггг] $bye *
            a: bye
        state: yes
            q: * (* да * | * вопрос * | * билет* ) *
            a: Обратимся к админу
    
 
            
    
    
    
    state: NoMatch
        event!: noMatch
        a: Я не понял. Вы сказали: {{$request.query}}

    state: Match
        event!: match
        a: {{$context.intent.answer}}
    
    