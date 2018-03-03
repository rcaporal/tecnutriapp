# Tecnutriapp

Aplicativo desenvolvido de acordo com as seguintes especificações:

 - Implementação em Java
 - Banner fixo na parte inferior da tela utilizando o serviço MoPub para alimentação
 - Integração com o Firebase Remote Config com o parâmetro "show_ads" para ligar ou desligar a publicidade.
 - Integração com Fabric.io.
 - Carregamento de imagens utilizando Picasso.
 - Requisições de endpoints utilizando Retrofit.
 - Armazenamento de dados locais utilizando Realm.
 - Dois flavours distintos com cores diferente (Laranja e Ciano).
 - Contém os idiomas 'pt' e 'en'.

## Tela 1 - Feed Activity

<img width="200" alt="Tela 1 - Feed Activity" src="https://i.imgur.com/qHmq4YV.png">

#### Comportamento: OK
  * Um json é recebido do endpoint com uma série de posts (key: "items") com cada um dos cards que irá mostrar. Ao tocar no perfil do usuário, abre-se a tela do perfil (Tela 3 - Profile Activity), ou ao tocar no resto do card, abre-se a tela dos detalhes do post (Tela 2 - Post Details Activity).

#### Paginação: OK
  * Requisições são geradas automaticamente de acordo com a proximidade do final da página carregada. Uma nova requisição é criada utilizando como parâmetros os campos "p" (página) e "t" (timestamp).
  
#### Reload: OK
  * A acivity possui um pull-to-refresh que quando acionado remove as páginas atuais e em seguida carrega uma nova.
  
#### Curtir: OK
  * Cada publicação apresenta um botão "curtir" que quando acionado salva seu status localmente utilizando Realm.



## Tela 2 - Post Details Activity

<img width="200" alt="Tela 2 - Post Details Activity" src="https://i.imgur.com/tGCDbzl.png">

#### Comportamento: OK
  * Um json é recebido do endpoint com um único post com os detalhes da refeição. Ao tocar no perfil do usuário, abre-se a tela do perfil (Tela 3 - Profile Activity), é possível voltar para a tela de feed. 
  
#### Reload: OK
  * A acivity possui um pull-to-refresh que quando acionado atualiza as informações da postagem.
  
#### Curtir: OK
  * A publicação apresenta um botão "curtir" que tem seu status inicializado de acordo com as informações locais contidas no Realm. O botão "curtir" quando acionado salva seu status localmente utilizando Realm e gera uma mensagem de broadcast utilizando EventBus para as activities anteriores atualizarem seus respectivos botões mantendo assim a consistência.



## Tela 3 - Profile Activity

<img width="200" alt="Tela 3 - Profile Activity" src="https://i.imgur.com/F9L8bM4.png">

#### Comṕortamento: OK
  * Um json é recebido do endpoint com os dados do perfil do usuário e uma série de posts (key: "items") com cada um dos cards que irá mostrar. Ao tocar em algum card, abre-se a tela dos detalhes do post (Tela 2 - Post Details Activity).
  
#### Paginação: OK
  * Requisições são geradas automaticamente de acordo com a proximidade do final da página carregada. Uma nova requisição é criada utilizando como parâmetros os campos "p" (página) e "t" (timestamp).
  
#### Reload: OK
  * A acivity possui um pull-to-refresh que quando acionado remove as páginas atuais e em seguida carrega uma nova.
