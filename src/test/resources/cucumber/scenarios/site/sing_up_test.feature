#language:ru
Функция: Проверка входа пользователя
  Открываем главную страницу
  Нажимаем на кнопку "Login"
  Проверяем что перешли на страницу авторизации
  Вводим имя пользователя в поле логина
  Вводим пароль в поле пароля
  Нажимаем на кнопку "Login"
  Проверяем что перешли на главную страницу
  Нажимаем на "logout"
  Проверяем что перешли на главную страницу
  И нам доступна кнопка "login"

  Структура сценария: Проверки входа пользователя
    Пусть откроется страница "http://localhost:8080/"
    Затем нажмем кнопку "body > header > div.header-area > div > div > div.col-md-8 > ul > li:nth-child(5) > a"
    Тогда откроется страница "http://localhost:8080/login"
    Затем вводим "<name>" в поле "#login_field"
    * вводим "<password>" в поле "#password_field"
    Затем нажмем кнопку "body > div > div > form > div.modal-footer > button.btn.btn-success"
    Тогда откроется страница "http://localhost:8080/"
    Затем нажмем кнопку "body > header > div.header-area > div > div > div.col-md-8 > ul > li:nth-child(5) > form > button"
    Тогда откроется страница "http://localhost:8080/"
    И доступен элемент "body > header > div.header-area > div > div > div.col-md-8 > ul > li:nth-child(5) > a"


    Примеры:
      | name  | password |
      | admin | admin    |
      | user  | user     |

