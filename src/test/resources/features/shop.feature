# language: es
@Shop
Característica: Flujo de compra en SauceDemo
  Como usuario de SauceDemo
  Quiero realizar una compra completa
  Para validar el flujo E2E con Serenity Screenplay

  # 1) Tu escenario actual (parametrizado en el feature)
  Esquema del escenario: Compra exitosa de dos productos
    Dado que el usuario "<usuario>" se autentica en SauceDemo con la clave "<password>"
    Cuando agrega dos productos al carrito
    Y visualiza el carrito y completa el formulario de compra con "<nombre>", "<apellido>" y "<codigoPostal>"
    Entonces debería ver el mensaje "Thank you for your order!"

    Ejemplos:
      | usuario       | password     | nombre | apellido | codigoPostal |
      | standard_user | secret_sauce | Jorge  | Escobar  | 170801       |
      | standard_user | secret_sauce | Carlos | Armijos  | 123456       |

  # 2) Nuevo escenario que toma datos desde JSON
  @json
  Esquema del escenario: Compra exitosa con datos desde JSON
    Dado cargo datos de compra de "orders.json" en la fila <idx>
    Y que el usuario se autentica en SauceDemo
    Cuando agrega dos productos al carrito
    Y visualiza el carrito y completa el formulario de compra
    Entonces debería ver el mensaje "Thank you for your order!"

    Ejemplos:
      | idx |
      | 0   |
      | 1   |
