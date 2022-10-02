Приложение представляет собой волонтерский сервис для помощи окружающим.

Имеется 5 основных экранов.

Профиль.
Основные данные пользователя, список друзей. Загрузка данных осуществляется с внутреннего репозитория, изображений со стороннего ресурса.
Есть возможность выйти из аккаунта с последующей блокировкой функционала приложения до повторного входа в профиль (логин: admin, пародь:password).

Истории.
Экран не реализован.

Помочь.
Представляет собой таблицу с возможными категориями мероприятий. Функционал ограничен всплывающими сообщениями с наименованием выбранной категорией.

Поиск.
Загружает из внутреннего репозитория список мероприятий. Реализована функция фильрации по мероприятия в зависимости от поискового запроса. 
Реализован ViewPager2 при переключении поиска между мероприятиями и организациями.

Новости.
Загружает таблицу состоящую из карточек с новостями. 
Загрузка осуществляетс: при подключении к сети - с сервера в формате json, при отсутствии сети - из сохраненной базы данных (обновляется при загрузке с сервера).
На карточке отображается фотография мероприятия, название, краткое описание и информация о датах проведения.
Нажатие на карточку открывает экран с более подробным описанием мероприятия, загрузка данных аналогична загрузке основного экрана новостей.
