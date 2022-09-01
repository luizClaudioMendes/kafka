# Kafka
iniciado em 18/08/2022

terminado em 

[certificate]() 

- [Kafka](#kafka)
- [Kafka: Produtores, Consumidores e streams](#kafka-produtores-consumidores-e-streams)
  - [Produtores e consumidores](#produtores-e-consumidores)
    - [Mensageria e Kafka](#mensageria-e-kafka)
    - [Instalando o Kafka localmente](#instalando-o-kafka-localmente)
      - [Erro comum na instalação do kafka](#erro-comum-na-instalação-do-kafka)
      - [bin/kafka-server-start config/server.properties](#binkafka-server-start-configserverproperties)
      - [local de persistencia do kakfa](#local-de-persistencia-do-kakfa)
        - [zookeeper](#zookeeper)
        - [start do zookeeper](#start-do-zookeeper)
      - [Criar um topico pela linha de comando](#criar-um-topico-pela-linha-de-comando)
      - [list de topicos](#list-de-topicos)
      - [criar um produtor pela linha de comando](#criar-um-produtor-pela-linha-de-comando)
      - [criar um consumer pela linha de comando (mensagens novas)](#criar-um-consumer-pela-linha-de-comando-mensagens-novas)
      - [criar um consumer pela linha de comando (todas as mensagens)](#criar-um-consumer-pela-linha-de-comando-todas-as-mensagens)
    - [Criando produtores em Java](#criando-produtores-em-java)
      - [Dependencias maven kafka](#dependencias-maven-kafka)
      - [Kafka producer](#kafka-producer)
      - [Metodo Send()](#metodo-send)
      - [Metodo Send().get()](#metodo-sendget)
      - [callback do send()](#callback-do-send)
      - [offset](#offset)
      - [Criando consumidores em Java](#criando-consumidores-em-java)
        - [Consumer](#consumer)
      - [Consumer Group](#consumer-group)
      - [Rebalancing entre o mesmo Consumer Group](#rebalancing-entre-o-mesmo-consumer-group)
      - [poll](#poll)
    - [O que aprendemos?](#o-que-aprendemos)
  - [Paralelisando tarefas em um serviço](#paralelisando-tarefas-em-um-serviço)
    - [Vários consumidores e produtores](#vários-consumidores-e-produtores)
    - [Paralelizando e a importância das keys](#paralelizando-e-a-importância-das-keys)
      - [Paralelismo no kafka (rebalance)](#paralelismo-no-kafka-rebalance)
      - [Partiçoes (partitions)](#partiçoes-partitions)
      - [alterar um topico ja existente pela linha de comando (kafka-topics.bat --alter)](#alterar-um-topico-ja-existente-pela-linha-de-comando-kafka-topicsbat---alter)
      - [como o kafka faz o rebalanceamento das mensagens (direcionamento)](#como-o-kafka-faz-o-rebalanceamento-das-mensagens-direcionamento)
      - [kafka-consumer-groups (kafka-consumer-groups.bat --all-groups)](#kafka-consumer-groups-kafka-consumer-groupsbat---all-groups)
      - [Client ID no consumer](#client-id-no-consumer)
    - [Max poll e dando mais chances para auto commit](#max-poll-e-dando-mais-chances-para-auto-commit)
      - [Poll](#poll-1)
      - [Maximo de records consumidos](#maximo-de-records-consumidos)
    - [O que aprendemos?](#o-que-aprendemos-1)
  - [Criando nossa camada](#criando-nossa-camada)
    - [Extraindo uma camada de consumidor (refactor)](#extraindo-uma-camada-de-consumidor-refactor)
    - [Extraindo nossa camada de producer](#extraindo-nossa-camada-de-producer)
    - [](#)


# Kafka: Produtores, Consumidores e streams

## Produtores e consumidores
### Mensageria e Kafka

Imagina um sistema de e-commerce, se é um sistema de e-commerce, o que que eu vou ter nele? 

Eu vou ter, por exemplo, o meu usuário acessando o meu sistema online. 

Então aqui dentro do meu usuário, que é o meu cliente, o meu navegador, que eu vou chamar de usuário.

Então no navegador, esse meu cliente está acessando o quê? 

A web e através da web, acessa um servidor Http. 

Então o acesso a esse servidor Http é feito e funciona. 

Agora, o que mais que acontece? 

Então, repara que o servidor Http tem diversas tarefas para fazer, por quê? 

Porque é um processo de compra, você está efetuando uma compra. 

Então, o que que eu tenho que fazer?

Eu tenho que verificar se é uma fraude, enviar um e-mail dizendo: “Olha, a sua compra está sendo processada”. 

Aí, se for fraude, eu te notifico.

se não for fraude, o que que eu tenho que fazer? 

Eu tenho que de alguma maneira efetuar a compra, o pagamento de verdade. 

Se o pagamento for um sucesso, eu tenho que liberar o produto, por exemplo, se é um produto online, como um e-book.

Então eu tenho que gerar o e-book com a versão customizada para aquele usuário ou usuária, que tem um nome daquela pessoa, o CPF, etc., e aí, sim, enviar esse produto por e-mail. 

Então, repara que vai ficando cada vez um passo depois do outro, um passo depois do outro, cada vez mais complexo.

Uma coisa cada vez maior, cada vez maior. 

Então o servidor, ele envia um e-mail. 

Aí, você fala: “Guilherme, ele poderia fazer a fraude?”.

Depois do e-mail, um e-mail do tipo, recebi o seu pedido, então estou processando por fraude. 

Então, poderia ser, depois, é verdade? 

Eu poderia fazer isso aqui, nessa ordem e colocar todo esse código dentro de um único sistema, funcionaria. 

Quer dizer, primeiro o meu servidor http, o que que ele vai fazer?

Lá dentro, ele envia um e-mail. 

O e-mail foi enviado? 

Verifica se é fraude. 

É fraude? 

Faz tal coisa e por aí vai. 

Tudo dentro de um grande programa, uma linha depois da outra, um problema disso, muito direto para os sistemas é que, por exemplo, esperar o e-mail. tem que esperar um sistema externo me responder, o meu servidor SMTP, que envia e-mails.

E pode ser que esse servidor esteja fora do ar, pode ser que esse servidor esteja lerdo, pode ser que não sei o quê. 

eu vou demorar para começar o processo de fraude, porque eu estou esperando o e-mail. 

Então é muito comum que em sistemas web, **a gente queira dar uma resposta para o nosso usuário, o mais rápido possível**.

Isso é muito comum. 

Então é muito comum que esse tipo de tarefa, a gente faça em paralelo, a gente não faça isso sequencial. 

Então, ele dispara o e-mail dispara o sistema de verificar se é fraude ao mesmo tempo. 

O que que quer dizer, dispara o e-mail, dispara o sistema, ele vai verifica se é fraude?

Quer dizer que pode ser no mesmo computador duas threads, quer dizer que pode ser eu me comunicando com dois computadores diferentes e falando: “Olha, o computador aí que tem outro programa, envia um e-mail, uma requisição http, via rest”, seja o lá o que for.

Aqui também, uma requisição http via rest ou uma outra thread, falando: “Detecta aí a fraude” e enquanto isso, eu já dei uma resposta aqui para o meu cliente dizendo: “Sua compra está sendo processada” e agora eu vou processando tudo isso em paralelo, na mesma máquina, em máquinas distintas, são opções.

Essa comunicação feita via http, via rest, via outro tipo de mensageria, etc., são várias opções, o tradicional seria, primeiro na mesma máquina com várias threads, depois em máquinas distintas se comunicando via http, que é uma maneira comum de ser feito isso, entre sistemas.

Então, eu detecto é fraude. 

Se é fraude ou não, eu vou ter que fazer alguma coisa.

Efetuar o pagamento, Depois que eu cobrei do cartão de crédito, o que que eu tenho que fazer? 

Se for sucesso, eu tenho que preparar o envio, vamos pensar num bem digital, como um PDF, um E-book.

Então, nesse caso, teria que gerar o PDF e por fim, eu teria que enviar o e-mail do PDF, então tudo isso teria que acontecer. 

Aqui, parece ter que ser sequencial, né? 

Porque eu não vou gerar o PDF, antes de gerar o pagamento ou pelo menos enviar o e-mail, eu não vou, antes de confirmar o pagamento, provavelmente.

Então, você vai definir a ordem, o que que você quer fazer em paralelo ou não, mas você começa a ter agora mais várias setinhas, eu tenho aqui uma setinha da fraude, desse sistema de fraude ou desse código de fraude, para essa próxima parte.

Aí, eu tenho do sistema de efetuar pagamento ou do código de efetuar pagamento, para gerar o PDF e por aí, vai. 

Eu estou tendo até dificuldade de tanta setinha que eu tenho aqui, eu vou tendo dificuldade de arrastar esses caras, porque é muita coisa que tem que ser feita sequencialmente ou um código que chama http e o outro código.

E para pra pensar, isso é no caro de sucesso, efetuei o pagamento e no caso de fracasso? 

“Ah, no caso de fracasse, por exemplo, do pagamento, eu gostaria de também enviar um e-mail, eu também vou ter que enviar um e-mail de fracasso, nesse caso”.

Então, nesse caso aqui, eu tenho que enviar um e-mail também, eu vou ter que ter um outro cara aqui, que é enviar o e-mail, que na verdade, não é e-mail do PDF, “Enviar o e-mail do fracasso”. Além disso tudo, eu gostaria, que eu tivesse suporte à produtos físicos.

Então, se é produto físico, eu tenho um estoque e na hora que você pediu a compra, eu já tenho que reservar esse estoque para você. 

Então, eu tenho que aqui, logo de cara, já reservar o estoque, tem que reservar o estoque, então eu vou e reservo o estoque.

Então eu tenho três... se são máquinas diferentes, com serviços diferentes, três requisições https, uma requisição http, outra requisição, outra, outra e por aí, vai. 

Ah, e calma aí, a compra foi confirmada? 

Efetuou o pagamento e a compra foi confirmada? E se ela for digital, o que que eu tenho que fazer?

Eu tenho que confirmar do estoque, eu reservei? 

Agora eu tenho que confirmar o estoque, confirmar que vai embora, confirmar o estoque e se deu fracasso, o que que eu tenho que fazer? 

Possivelmente, vai depender de como você quer fazer, a ordem, cancelar do estoque, só se for um produto físico.

Então, se for esse caso, você vai ter que cancelar do estoque, se for físico. 

Calma aí, está ficando cada vez mais complexo e isso tudo aqui, eu posso ir falar: “Ah, eu tenho um serviço de estoque, que tem essas três URIs”, “Eu tenho o serviço de fraude, que em essa URI”, “O serviço de pagamento que tem essa URI”, “Eu tenho o serviço de PDF, que é essa URI”, “eu tenho o serviço de e-mail, que são essas três URIs”.

Mas todas essas comunicações, todas essas setas, sou eu Guilherme quem programo, sou em quem faço todas elas. 

Eu sei quem está na outra ponta e envio uma mensagem http ou a gente pode definir outro termo, de acordo como essa requisição é feita, notificando o que eu gostaria que fosse feito ou algo do gênero.

Vamos complicar mais ainda, como no mundo real. 

Tudo isso daqui, eu preciso de log, então eu preciso de toda a vez que eu disparo um e-mail, logar em algum lugar, que foi disparado um e-mail. 

Então, eu preciso de um sistema de log ou de algo de log, que eu tenha o quê?

Tudo o que acontece agora vamos lá, “Boa sorte Guilherme”, sofre aqui é quem desenha, quem tem que ficar desenhando na mão as coisas. Todo mundo aqui tem que fazer o quê? Acessar o sistema de log, porque tudo o que acontece, tem que ir para o sistema de log.

Tudo o que acontece, tem que ir para o sistema de log, por quê? 

Se eu quiser fazer uma auditoria, saber a ordem que aconteceram as coisas, qualquer coisa do gênero, tem que ir para o sistema de log. 

Então, reparem, eu nem vou terminar tudo do sistema de log e já está uma bagunça do tamanho de outro planeta, por quê?

Porque vários sistemas, conhecem vários sistemas. 

Vários sistemas sabem como outro sistema funciona e você começa a ficar com esse emaranhado de todo mundo se conhecer e todo mundo saber como o outro funciona e todo mundo saber qual é o próximo passo e qual é o passo anterior, de onde que veio, para onde que vai e não sei o quê, todo mundo com tudo.

Mais ainda, além do log, tem outras preocupações, outros concerns, que cortam a nossa aplicação inteira, que são Cross Cutting Concerns, o que que tem isso, por exemplo? 

Os dados... o Analytics, eu quero saber, como que a gente está de fraude, hoje a gente está com 10%? 

10% é a nossa média histórica.

Se hoje está 20%, opa, algo está acontecendo de errado com o nosso sistema de fraude ou com os fraudadores ou a gente está com o sistema de fraude... deu uma zoada hoje e aí, está detectando 20% de fraude, quando o normal 10, tem anos que o normal é 10%, estou citando exemplos.

Então aconteceu alguma coisa hoje de estranha com o meu sistema ou realmente os fraudadores estão fazendo um ataque, alguma coisa, para tentar fraudar o meu sistema. 

Então, eu preciso de um Analytics, para acompanhar as métricas, para saber, tem alguma coisa fora do ar?

Tem alguma coisa que não está dando conta? 

Tem alguma coisa que está dando mais erro do que o comum? 

Então, calma aí, se esse tipo de coisa, eu preciso saber, não só para fraude, eu preciso saber também para o pagamento. 

A taxa de pagamento está como a taxa histórica de sucesso ou a taxa de sucesso não está bem assim?

A taxa de e-mails que são enviados com sucesso, que não são bounce, que não bate e volta, está na minha taxa normal ou está batendo e voltando mais? 

Então, quer dizer que os servidores de e-mail estão achando que eu sou spam e eu tenho que fazer alguma coisa? 

Analytics.

“Ah, os PDFs, que eu estou gerando, eu estou gerando num ritmo que eu esperava ou não, estou gerando muito mais?” 

Então, deu algum bug ali e entrou num loop infinito ou não, estou gerando a menos. 

Então ou é vendas a menos ou o sistema está lerdo e está acumulando de PDFs serem gerados.

Analytics, tudo coisas Analytics. 

Então, repara, olha o fuzuê desse sistema, por que que a gente tem esse fuzuê? 

Porque todo mundo conhece todo mundo, fica esse fuzuê total e aí, você tem que conhecer com quem você vai chamar requisição e é síncrono, só que é externo, mas se esse cara caiu, como que eu tenho que reagir com esse?

Imagina, o Analytics caiu e aí, a fraude, o que que ela faz? 

Ela vai para a próxima fase ou ela espera o Analytics? 

O que que ela faz? 

Como é que eu faço para, tipo: “Não, não, vai para a próxima fase e daqui a pouco, quando o Analytics subir, eu aviso o Analytics. 

Boa sorte, implementar isso, boa sorte, por quê?

Porque se o seu sistema de fraude cai agora, onde é que você anatou que tinha notificar o Analytics daqui um tempo? 

Aí é claro, você tem sistemas (poling), tem sistemas (wacher), tem sistema de (observers),.

você começa a criar várias estruturas complexas, para tentar lidar com essa complexidade dos processos, do processo externo que a gente tem aqui dentro, que deixou de ser sequencial, passou a ser paralelo, por quê? 

Porque com isso a gente potencializa o desempenho da nossa aplicação.

Então, eu posso executar 10 máquinas de fraude e uma só de e-mail, mas se de repente, efetuar o pagamento também, é uma coisa que precisa de muita máquina, tenho cinco máquinas, então eu posso escalar cada um desses serviços com máquinas distintas.

Então, eu tenho essa vantagem de estar com tudo isso distribuído e paralelizado. 

Então, repara a bagunça que é trabalhar com esse tipo de sistema dessa forma é claro, existem sistemas e formas de trabalhar mais inteligentes ou pelo menos diferentes, que vão trazer certas vantagens nessas abordagens.

Vamos dar uma pensadinha, como a gente pode fazer isso? 

Porque a ideia é que, eu não quero fazer com que eles se conheçam, eles não precisam se conhecer, por exemplo, claro, quando o meu cliente, que eu não copiei aqui, que é o meu usuário, o navegador, podia ser um aplicativo, podia ser outra coisa, que é o usuário final.

Acessa o servidor http? 

Claro, conhece, está fazendo uma requisição, poderia ser um aplicativo fazendo uma requisição http, o que fosse. 

A partir daqui, o servidor http recebeu novo pedido de compra? 

Ele faz o quê? 

Ele simplesmente envia uma mensagem que se chama: novo pedido de compra.

Então, eu tenho o meu broker, que é quem recebe mensagens. 

Então, eu simplesmente mando uma mensagem para o meu broker. 

Eu falo: “Broker, olha, toma aí, uma mensagem para você”.

E aí, quando eu mando a mensagem para o broker, eu falo: “Olha, a minha mensagem é de nova compra”, ou “Novo pedido de compra”. 

Eu mando essa mensagem e eu não sei quem vai receber isso, eu não sei e não importa quem vai receber isso, por quê?

Porque o e-mail, que é disparado, quando tem um novo pedido de compra, ele está escutando esse tópico, ele está escutando esse assunto dessa mensagem. 

O assunto da nova compra, ele está escutando, mas não só ele, o fraude também está escutando isso.

O fraude também, mas não só isso, o reservar o estoque, também está escutando esse tópico e não só isso, o Analytics também está escutando esse tópico e o log também está escutando esse tópico, todos esses estão escutando esse tópico.

O servidor http sabe alguma coisa sobre isso? 

Não, ele simplesmente envia uma mensagem falando: “Tenho um novo pedido de compra, aqui estão as informações”, todos esses sistemas estão escutando esse tópico, cada um faz a sua tarefa de forma síncrona, na mesma máquina ou máquinas distribuídas, não estou nem aí.

Então, para simplificar, eu nem preciso das setas, eu só preciso dizer que esse serviço que está rodando numa máquina escuta o “Nova compra”, vou colocar assim: “Nova compra”. 

O fraude está escutando o “Nova compra”, o reservar o estoque está escutando o “Nova compra”.

O Analytics está escutando o “Nova compra” e o log está escutando o “Nova compra”, o que que acontece? 

Quando o e-mail é enviado, o que que acontece? 

O sistema de e-mails, o serviço de e-mail envia uma mensagem para o broker, falando o quê?

Ele fala, assim: “Olha, eu terminei aqui a minha parte: e-mail enviado”.

E aí, quem está escutando “e-mail enviado”? 

O log, está escutando “e-mail enviado” e o Analytics está escutando “e-mail enviado”, reparou? 

O que mais? 

E quando a fraude deu sucesso? 

Não tem fraude, o que que a gente vai querer fazer nesse instante?

Nesse instante, a gente vai querer validar o pagamento. Então, eu vou copiar para cá e vou colocar: “Efetua o pagamento”. 

Efetua o pagamento, tem que fazer o quê? 

Tem que escutar o tópico de “Compra sem fraude”, certo?

Então é: “Compra sem fraude”, por exemplo, tá? 

Você poderia falar outra coisa, outro tópico, você define o tópico, etc., eu estou usando esse exemplo aqui agora, “Compra sem fraude”. 

Então, o fraude envia essa mensagem, o fraude vai enviar a mensagem e qual mensagem que ele envia mesmo?

Ele envia a mensagem: “Compra sem fraude”. 

Quem está escutando esse tópico? 

O efetua pagamento, o log e o Analytics. 

Então, repara aqui o que está acontecendo, da maneira que eu estou desenhando os meus sistemas, eu estou falando assim: “Olha, não me importa quem vai escutar o meu status, uma atualização de status, uma situação que ocorreu no meu sistema; ocorreu uma nova compra, um pedido de nova compra; ocorreu um e-mail enviado; ocorreu que a compra foi validada sem fraude; ocorreu que o pagamento foi efetuado com sucesso; ocorreu que o PDF foi gerado com sucesso” .

Essas coisas ocorreram, quem está escutando isso para agir, não me importa e isso é o conceito de mensageria, esse conceito de mensageria, de troca de mensagens aparece em diversos sistemas, diversas implementações. 

O Kafka tem algumas sacadas aqui, algumas são comuns à mensageria e o Kafka tem algumas sacadas especiais dele.

Então, uma das sacadas aqui de mensageria é: eu posso ter quantos servidores e serviços rodando de e-mail eu quiser, como eu disse antes, como funcionava antes com o próprio http, se o sistema de fraude é um sistema que consome muito a CPU e pouca memória, eu posso ter várias máquinas com CPUs potentes rodando isso.

Se o sistema de gerar PDF, consome pouca CPU, mas muita memória, eu posso ter algumas máquinas com o CPU mediano e bastante memória para elas. Então, você pode escalar de acordo com o necessário.

Além disso... Então quer ser, você tira um pontinho de falha aqui, se eu tivesse só uma máquina rodando de fraude, se caísse, eu me dava mal, se eu tenho 10 máquinas rodando, se uma cai, eu ainda tenho nove e por aí, vai, vou tirando os pontos de falha, condicionando redundância.

O broker também, você pode replicar, você não precisa ter um único broker rodando, você poder ter um cluster de brokers. 

Então, eu tenho um cluster com três brokers, por exemplo ou um cluster com 30 brokers, rodando 30 instâncias do Kafka, estou dando exemplo.

Então, o que acontece? 

Quando você envia uma mensagem, essa mensagem vai parar possivelmente em mais de um broker, por quê? 

Porque se um broker desligar, o outro broker ainda tem essa mensagem. 

Então, você começa a ter mais tolerância a falha ainda, por quê?

Porque se você manda uma mensagem, qualquer mensagem e a mensagem está armazenada em três máquinas, até ela ser recebida, até ela ser recebida por quem quiser, o que que acontece?

Se uma dessas máquinas cair, as outras duas ainda tem essa mensagem, se uma dessas máquinas pegar fogo, as outras duas ainda teme essa mensagem. 

Então, você ganha mais reliability, você consegue garantir que as coisas vão estar lá, serão recebidas, etc.

Mais ainda, você consegue rodar isso em paralelo, como a gente está fazendo. 

Você consegue com que esses dados, os dados das mensagens que chegam, sejam distribuídos para várias instâncias do fraudador, do detector de fraude.

Então, se eu recebi cinco mensagens de novas compras, eu posso mandar dois para um, dois para o outro, um para o outro, por exemplo, se eu tenho três instâncias de fraudador, de detecção de fraude.

na verdade, se os sistema de fraudes aqui caírem e voltarem só amanhã, porque deu um pau aqui no meu sistema, não tem problema, as mensagens estão armazenadas aqui e eu consigo executar elas um dia depois.

Não tem problema, se caiu, “Falhou”, aquelas minhas 10 máquinas caíram por algum motivo e eu não estou conseguindo levantar, eu consigo armazenar essa mensagem por um tempo que eu configuro, posso configurar um tempo ou quantidade de espaço em disco, que eu quero gravar as mensagens, sem problemas.

Eu posso também falar: “Olha, pensa bem, se a compra de um usuário foi fraude, eu não quero executar as outras desse usuário”, poderia ser uma definição do sistema, pode ser.

Então, o que que você pode fazer? 

Você pode no Kafka, por mais que a gente execute em paralelo, Você pode em determinados momentos falar: “Olha, as compras para determinado usuário, na mensagem de gerar PDF, eu quero que gerem em sequência”, por quê?

Porque se um usuário comprou mil PDFs, eu não quero que fique gerando os mil PDFs daquele usuário e a galera fique esperando, eu quero ir gerando um PDF para cada um. 

A pessoa não vai ler os mil de uma vez, então todo mundo está lendo alguma coisa, por exemplo.

Então, você poderia definir regras do gênero, de que olha: “Apesar de que eu quero paralelizável, quando eu penso em um usuário, eu quero que os daquele usuário execute sequencial”.

Por exemplo, o estoque, eu reservar o estoque, eu posso executar em paralelo, mas para um produto específico, provavelmente, eu quero reservar o estoque sequencial, eu quero tirar de lá de dentro o do estoque reservado, o estoque em sequência para o produto cinco.

Mas, para o produto cinco e para o produto 15, eu posso executar em duas máquinas, em paralelo, não estou nem aí. 

Então, eu posso usar o produto como chave para serializar a execução, deixar em sequência.

Tudo isso, o Kafka é capaz de fazer.

### Instalando o Kafka localmente
Então, primeiro em kafka.apache.org, eu venho aqui na parte de download e vou baixar a última versão 231 com a versão mais recente de Scala que é a 2.12.

Então esse é o TGZ que eu vou baixar. 

Baixando o TGZ, você pode descompactar ele, dar dois cliques, use o terminal que você preferir, eu vou pelo terminal, assim eu mostro um problema super comum que a gente tem quando roda o Kafka.

#### Erro comum na instalação do kafka
Então, eu estou dentro do diretório anterior, vou entrar no diretório apps(espaço)descompactadas, de proposito, tar zxf. 

No diretório meu de download, eu tenho um kafka, descompactei entro no diretório do Kafka, estou dentro do diretório do Kafka.

Aqui dentro, você vai ver que a gente tem vários diretórios, o de scripts e o de configurações. 

Então, eu quero rodar, dentro do diretório bin, kafka-server-start.sh, levando o servidor, com a configuração padrão de servidor.

```
bin/kafka-server-start config/server.properties
```

E aí, eu tento rodar e ele dá um monte de erro, o erro, parece que o projeto não está construído, mas não é isso, lembrem, a gente baixou a versão, a versão que eu baixei é a versão binária, com o projeto já construído, já está binário, já da para executar, não foi o código fonte que eu baixei, não baixei o código fonte, eu baixei a binária.

Então, qual que é o problema? 

O problema está aqui antes, o diretório path que eu estou usando, no meu path, 'apps descompactadas' tem um espaço e aí, nesse espaço, o kafka fica doido. 

Então, o que que a gente vai ter que fazer? 

A gente vai ter que renomear esse diretório aqui, para simplesmente apps.

Então, eu vou voltar aqui para trás, só para eu não me perder, estou no diretório anterior, eu vou renomear isso aqui só para apps, vou entrar nessas apps, agora sim, dentro do kafka e agora sim eu posso rodar. 

#### bin/kafka-server-start config/server.properties
Então, eu vou tentar bin/kafka-server-start config/server.properties

```
bin/kafka-server-start config/server.properties
```

Tento executar, ele roda o meu Java, eu estou com o Java 13, só que ele começa a dar vários erros. 

Ele fala: “Erro, erro, erro, não consegui, estou desligando” e ele desliga, por quê?

#### local de persistencia do kakfa
Porque o Kafka, ele é o processador dessas mensagens, de jogar de um lado para cá, para o outro lado, de cá, para lá, não sei o quê, não sei o quê, que lida com todos os strings e etc., processador nesse sentido de conectar tudo, mas onde que armazena essas informações?

##### zookeeper
Algumas informações básicas, o Kafka tem que armazenar em algum lugar e o lugar onde o Kafka armazena, isso por padrão, se chama zookeeper. 

Então, a gente teria que baixar também o zookeeper, zookeeper download. 

Claro, o Kafka já vem com o zookeeper instalado, caso você não queria instalar separadamente, porque pode ter empresas que já tenham o zookeeper rodando por outros motivos. 

No nosso caso, a gente não tem, então o que que eu vou fazer?

##### start do zookeeper
Antes de rodar o Kafka, vou executar bin/zoopeeper, adivinha? 
Server-start e adivinha, preciso de configuração, config/zookeeper.properties. 

Então, eu já tenho as propriedades padrão aqui, já configuradas, vou utilizar elas.

```
bin/zookeeper-server-start.sh config/zookeeper.properties
```

Então, ele vai levantar para mim, olha, aqui embaixo, conectei com o 0.0.0.0, aqui, qualquer lugar para conectar, etc., na porta 2181. 

Então, o zookeeper está rodando, agora, sim, bin/kafka-server-start config/server.properties, vou tentar e aí, ele vai tentar conectar com o Zookeeper, e ele fala lá: “Conectei com o zookeeper”, manda um monte de propriedades padrão que ele está utilizando, etc., e no final, você vai ver que olha: “Started”, no final vai estar “Started”.

Então, ele está rodando o Kafka em algum canto para a gente. 

A gente pode até procurar aqui ó, porta, 9092, a porta padrão 9092, que vem no server properties, está escrito no server properties.

Então, a gente tem a propriedade que está na porta 9092, rodando o Kafka, por trás, usou keeper para algumas configurações, não todos os dados, algumas configurações. 

Vamos então agora, enviar uma mensagem de um lado para o outro. Eu quero ver o Kafka rodando, vamos testar?

#### Criar um topico pela linha de comando
Então, a gente vai testar um terminal de novo no mesmo diretório, Kafka, lembra, a gente vai usar várias coisas a partir daqui e o que eu vou querer fazer é criar um tópico, para a gente poder trocar mensagens. 

Então, bin/kafka-tópicos.sh, e aí, eu posso ver tudo o que o kafka topics permite que eu faça, tem várias coisas.

```
bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic LOJA_NOVO_PEDIDO
```

O que a gente vai fazer é um create, criar um tópico, só que a gente precisa falar aonde que está o Kafka rodando, lembra? 

O Kafka está rodando, bootstrap-server em localhost, porta 9092. 

Então, conecta com o Kafka localhost 9092. 

E aí, eu falo alguma propriedades do meu tópico.

Eu vou falar, colocar duas propriedades padrão aqui simples, que a gente vai trabalhar com elas durante todos os cursos, então não se preocupe, a gente vai entrar afundo nelas e brincar com elas, ver o que acontece com elas aos poucos. 

Por enquanto, eu só queria deixar fixo, replication factor como 1 e o partitions como 1.

E eu vou falar o nome do tópico, o nome do tópico, você pode botar o padrão que você quiser, imagine que eu tenho na minha loja um novo pedido que está entrando, então eu poderia colocar loja_novopedido. 

Aí, você fala: “Ah, Guilherme, não poderia ser loja, ponto, underline aqui?”.

Poderia, a sugestão do kafka topics é que a gente **não use, não misture ponto com underline**, por isso eu não vou misturar. 

Eu vou manter aqui o underline como uma separação de meio que “subtópicos”, não são subtópicos, não existe esse conceito, é só uma separação para leitura.

Olha, loja teve um novo pedido, então a loja teve um novo pedido, só que você fica com a sensação, será que é o melhor padrão? 

Não existe muito melhor, pior padrão. 

Existe que ponto e underline não é uma recomendação de uso ao mesmo tempo pelo próprio Kafka.

Então, vou utilizar sempre o underline para separar as palavras aqui para mim, tudo em maiúsculo, você poderia definir que é tudo em minúsculo, poderia definir o que você quisesse como padrão.

No meu caso, eu vou utilizar esse e a sua empresa vai utilizar um padrão próprio, não tem problema. 

Então, vamos mandar criar esse tópico e quando a gente manda criar, ele fala aqui: "**De acordo com limitações nas métricas dos nomes, tópicos com período, ponto ou underscore podem colidir**".

Então, se você tem underscore, pode colidir, a melhor maneira é usar ou um ou outro, mas não ambos e a gente não está usando ambos, está usando só um. 

O problema então, seria se a gente usasse um ponto em alguns, underline em outros, poderia ter uma colisão.

Estou usando só underline, não tenho problema, você poderia usar outros caracteres, como o hífen e por aí, vai. 

Estou usando underline, padrão que você definir de acordo com o que você definir para a sua empresa. 

#### list de topicos 
Quero ter certeza que esse tópico foi criado, kafka-topics --list, de novo, eu tenho que falar qual é o servidor, bootstrap-server localhost 9092.

```
bin/kafka-topics.sh --list --bootstrap-server localhost:9092
```

Então, esses comandinhos são super úteis no dia-a-dia a gente vai usar várias vezes. 

Então, ele está falando: “Existe, sim, um tópico chamado: loja novo pedido”. 

Queria mandar algumas mensagens para esse tópico, só para a gente ver isso acontecendo, envio de mensagens, porque a gente vê no kafka, ele está falando: “Eu criei a partição para esse tópico”.

E está lá, partição 0, só existe uma partição, começa com 0 e por aí, vai. 

Então, a gente vê aí que o tópico realmente está lá. 

#### criar um produtor pela linha de comando
Eu quero rodar um produtor de mensagens, que produz mensagens, de novo, no terminal, porque a gente só quer confirmar a instalação, tudo ok, kafka, eu quero criar no console um produtor e eu vou falar para ele assim: “Olha, os brokers, os kafkas estão rodando aonde mesmo?”.

```
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic LOJA_NOVO_PEDIDO
```

Onde que eles estão rodando? 

Broker list, no localhost 9092 e eu falo o tópico, qual que é o tópico mesmo? 

Loja novo pedido. 

Quando eu rodo na linha de comando o console producer, cada linhazinha que eu digitar aqui é uma mensagem.


Então, eu poderia falar: “Olha, o pedido 0 com vírgula”, o valor foi R$ 560,00 e depois o pedido 1, o valor foi R$ 330,00, depois o pedido 2, que foi esse número aqui grandão. 

Então, estou mandando várias mensagens para esse tópico, a gente pode dar o list no nosso topics, para ver os tópicos que existem e o tópico loja novo pedido ainda existe.

#### criar um consumer pela linha de comando (mensagens novas)
O que eu quero fazer agora é consumir. 

Então, eu vou abrir uma nova aba, bin/kafka-console-consumer, bootstrap-server, então eu vou conectar bootstrap-server no localhost 9092 e o tópico loja novo pedido. 

```
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic LOJA_NOVO_PEDIDO
```

Agora, vem uma pergunta, eu devo consumir a partir de quando?

Desde lá de trás? 

Desde a primeira mensagem que está armazenada? 

Ou a partir das mensagem que chegam agora? 

#### criar um consumer pela linha de comando (todas as mensagens)
Se eu executo do jeito que está, Não veio mensagem nenhuma aqui, agora, se eu... 

Para que a gente possa usar o para cima e não ter que ficar digitando isso aqui toda a vez, 9092, o topic loja novo pedido, mas eu poderia falar: “Olha, começa do começo”, falar um beginning, então ele vai começar da primeira mensagem que está armazenada ainda no Kafka.

```
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic LOJA_NOVO_PEDIDO --from-beginning
```

Então, ele vai olhar lá para trás e vai falar: “Olha, de qual a mensagem que está armazenada?”, está armazenada essa mensagem, essa mensagem e essa mensagem. 

Então, aqui ele ainda não recebeu nada, porque não teve novas mensagens e aqui sim, aqui a gente teve três mensagens do passado, que estavam armazenadas esperando alguém consumir.

Então, vamos ver agora, vou mandar uma mensagem um pedido 3 com esse outro valor. 

Então, olha, aqui eu recebi o pedido 3 e aqui eu recebi o pedido 3, recebi nos dois consumidores.

É claro, a gente vai ver se a gente quer receber em todos os consumidores, em só um consumidor, como receber, quantas partições, quantas repetições, ter certeza que vai receber, certeza que começou desde o começo, várias coisas que a gente vai discutir durante o curso.

Mas isso daqui é legal para a gente ver como instalar o kafka, como olhar os tópicos que estão lá, a gente vai explorar o topics mais vezes... como gerar um consumidor, um produtor de strings, que manda strings simples e um consumidor ou mais de um consumidor, que consome essas strings, só para gente ver funcionando.

Claro, a partir de agora, a gente quer executar isso com programação e ver todas as vantagens e desvantagens que a gente vai ver com o kafka dentro dos nossos programas. São os nossos próximos passos.

### Criando produtores em Java
Agora que a gente está pronto para criar o nosso projeto, vamos criar ele. 

Lembrando, aqui nesse curso, a gente vai utilizar o projeto em Maven, que é feito em Java.

Eu estou utilizando a versão 13 do Java, você pode utilizar versões mais recentes, não tem problema. 

Então, dentro de Maven, eu vou criar um novo projeto, ele vai perguntar para mim qual é o grupo e o nome do projeto, então o meu grupo é br.com.alura e eu vou criar uma loja, um e-commerce, então “ecommerce”.

Então, next, vai perguntar o diretório para mim, esse é o diretório que eu quero criar, vou dar finish e ele cria para mim esse projeto. 

A gente está utilizando aqui Java, uma das linguagens mais utilizadas no mercado para coisas do gênero, mas você pode utilizar em qualquer linguagem, os problemas serão os mesmos e os desafios iguais e as soluções parecidas, não serão desafios de linguagem e sim desafios de mensageria distribuída, etc.

#### Dependencias maven kafka
Então, eu tenho aqui o meu projeto e eu posso colocar a dependência do Kafka aqui.

Então, a dependência que eu quero, se me chama Kafka-client, você pode procurar Kafka-Maven, Maven-Kafka-client ou a biblioteca que você usa em outra linguagem, claro, a ferramenta que você use gerenciamento. A versão mais recente aqui é o 231, vou clicar.

```
<!-- https://mvnrepository.com/artifact/org.apache.kafka/kafka-clients -->
<dependency>
    <groupId>org.apache.kafka</groupId>
    <artifactId>kafka-clients</artifactId>
    <version>3.2.1</version>
</dependency>

```

Eu quero baixar não só o Kafka-clients, mas eu quero baixar também o SLF4J, que é o sistema de log, simples, que é utilizado, pode ser uma implementação utilizada pelo Kafka e serve para a gente ver mensagens de log.

Não vou usar a versão alpha, não vou usar a versão beta, vou utilizar essa última versão, a versão mais recente, estável, para eu não ter problema, a gente não vai ficar configurando na unha, vai deixar o padrão e vou tirar o scope de teste. Não, não é só para teste, eu quero para valer.

```
<!-- https://mvnrepository.com/artifact/org.slf4j/slf4j-simple -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-simple</artifactId>
    <version>2.0.0</version>
    <scope>test</scope>
</dependency>

```

Então, a gente está tudo configurado, sincronizado, já baixou. 

Vamos no src, a gente tem aqui o Java. 

Vamos criar uma classe, que agora sim é o nosso projeto para valer. 

Esse nosso projeto, ele tem que criar, por exemplo, um cliente entrou no sistema e criou um pedido de compra.

Então, nesse momento, eu estou criando um novo pedido e um novo pedido de compra é um NewOrder. 

Então, eu estou fazendo o quê? 

Vai ser um programinha, eu vou chamar de Main, para deixar claro para a gente que isso daqui é um Main simples, que a gente executa, não é um serviço, não é nada, é só um Main que a gente executa de vez em quando, que vai criar um novo pedido de compra e o order.

Eu quero criar uma mensagem, enviar uma mensagem no Kafka, eu quero produzir uma mensagem, KafkaProducer, quando a gente tem um KafkaProducer, repara que ele precisa de dois parâmetros aqui de tipagem. 

O tipo da chave e o tipo da mensagem.

Por enquanto, vamos utilizar a strings para tudo, a medida que a gente entrar na chave, entrar na mensagem, a gente discute os tipos, então por enquanto só strings, tipos strings. 

E eu vou criar esse meu produtor, esse é o meu producer.

#### Kafka producer
Só que o new KafkaProducer recebe, se a gente passar o mouse, uma propriedade, o KafkaProducer precisa receber coisas, como properties.

Então, vamos criar propriedades aqui. 

Poderia ler de um arquivo. 

Quero criar na mão programaticamente com você, para a gente vê isso. 

Então, vamos criar aqui na mão um método estático, que devolve o quê?

Para mim um properties, eu estou feliz. 

Então, set propriedade, primeiro eu tenho que falar onde a gente vai se conectar. 

Lembra, quando a gente roda o produtor ou qualquer coisa do gênero, a gente tem que falar onde que está rodando os meus kafkas.

Então, os meus Kafkas, estão rodando na chave ProducerConfig, para a gente não errar a digitação, ProducerConfig, configuração de produtor, bootstrap server, lembra? E aqui dentro eu posso colocar os servidores, por exemplo, 172.0.0.1, que é o meu localhost, 9092, na porta 9092.


Que outras propriedades vão ser importantes? 

Eu vou querer uma segunda propriedade, que eu vou falar assim para ele: “Tanto a chave, quando o valor, os dois vão transformar a mensagem e a chave, baseado em strings”. Então, além do tipo, eu tenho que passar transformadores de strings para bytes, serializadores de strings para bytes.

Então, vai ter que falar: “O ProducerConfig, o Key serializer, é um serializador de quê? 

De strings, é um StringSerializer. 

Só que a gente tem que passar o nome dela, então getName, que eu estou passando o nome dessa classe. 

E a mesma coisa, igualzinho para o value, que é a mensagem, serializer class config, também vai estar utilizando o StringSerializer. 

Então, ambos vão utilizar o StringSerializer, quer dizer, vão serializar strings em bytes, o que mais?

Então, eu tenho o meu producer, agora que tem um producer, eu posso enviar alguma coisa, a maneira mais simples: producer.send. 

Então, eu quero enviar, vou enviar uma mensagem. 

A mensagem que eu vou enviar é um record, é um registro, por que um registro? 

Porque vai ficar registrado.

Essa mensagem vai ficar registrada no Kafka, por quanto tempo? 

Depende da configuração do seu server properties, “Ah, será que o espaço em disco pode acabar?” 

Pode, se deixar muito tempo, então também tem um server properties, qual o espaço máximo que vai armazenar as mensagens.

Então ou armazenar por espaço máximo ou por tempo máximo, configurações do seu server properties, você pode querer por só um dia, por só 5 minutos, por muitos dias, sem problemas, você configura isso. 

Então, no seu server properties, você pode configurar coisas do gênero.

https://docs.confluent.io/platform/current/installation/configuration/producer-configs.html

```

```

Então, o meu record, que é o meu registro, ele é o quê? 

Ele é um registro, um new Produtor Record, ele é um registro do meu record.

Lembrando, o ProducerRecord, também vai ter a chave e o valor, só que agora, para pra pensar, quando a gente criou um produtor, a gente falou o tópico... desculpa, a gente falou o IP, mas a gente falou o tópico também. 

Então aqui, o primeiro parâmetro é o tópico, qual que é o tópico que a gente está usando mesmo?

LOJA_NOVO_PEDIDO. 

Só um cuidado, a gente vai estar utilizando em inglês, então não vai ser mais loja novo pedido, vamos seguir o padrão do nosso projeto, no nosso projeto é “ECOMMERCE_NEW_ORDER”, a gente tem um novo pedido, um novo pedido de compra.

Então, esse daqui é o tópico que eu vou enviar a mensagem, o que mais? 

é só a chave e o valor. 

Por enquanto, eu vou mandar tanto para a chave, quando para o valor, a mesma coisa.

Quem é o value? 

A mensagem que eu quero mandar, “Ah, a mensagem que eu vou mandar, vai ser, por exemplo, o ID do meu pedido, o ID do meu usuário e o valor da compra, por exemplo”. 

Então, são três valores que eu estou mandando e eu estou usando isso tanto como chave, quanto como valor, tanto chave, quanto valor, estou usando a mesma coisa.

É claro, a gente vai ver como a chave é importante, etc., a medida do possível, mas por enquanto a gente não está preocupado.

Então, new ProducerRecord, eu tenho o record e eu envio esse record, envio o record e vejo o que acontece. 

Agora, eu tenho isso daqui compilando, o que que eu posso fazer? 

Esse “ECOMMERCE_NEW_ORDER” é o tópico que eu vou postar uma mensagem, então vamos testar? 

Então, para testar, clico com o da direita, Run.

Então, eu vou tentar rodar a primeira vez, ele deveria conectar aqui no nosso Kafka e tentar mandar a mensagem. 

Opa, alguma coisa aconteceu, alguma coisa aconteceu aqui, e-commerce new order, está falando e-commerce new order 0, está tentando mandar alguma coisa.

E a gente vê aqui o log, tem várias coisas, o log que começou e depois ele fala lá finalzinho, lá no finalzinho ele fala: “Tentei enviar e não encontrei nenhum líder para executar isso, alguma coisa aconteceu de errado”, ele tentou, mas ele não conseguiu no 127.0.0.1:9092, vamos dar uma olhada nos nossos tópicos.

Então, a gente vai olhar os tópicos. 

Opa, tem mais um tópico, a gente pode agora fazer um --describe, me descreva todos esses tópicos, por favor. 

```

```

Então, ele vai descrever...

Vamos descrever agora os tópicos e a gente tem aqui vários tópicos par a gente, mas se a gente olhar lá em cima, a gente vai ver o e-commerce new order e o loja novo pedido, a gente tem os dois tópicos aqui para a gente. 

Então, o primeiro tópico está aqui, o segundo tópico está aqui.

Então, repara que aqui no describe, a gente tem o e-commerce new order e o loja novo pedido, a gente tem os dois aqui, mas por que que ele deu um erro? 

Por que que ele falou que eu não encontrei um líder? 

Que um líder não estava disponível? 

Porque calma lá, a gente acabou de criar esse tópico, a gente acabou de criar.

Então, eu vou tentar rodar de novo. 

Deixa eu rodar de novo, Run, vou tentar rodar de novo e agora, será que foi? 

Você vem tudo para a direita e não tem log nenhum dizendo se foi ou não foi, será que foi? 

Será que não foi?

Vamos dar uma olhada, vou rodar o describe de novo e quando a gente rodar o describe, dá uma olhadinha aqui no resultado, lá em cima, e-commerce new order tem um partição, um líder e uma re. 

Então, calma lá, a gente não está conseguindo saber se a mensagem foi ou não foi, por que isso?

Porque dá uma olhadinha aqui no método send. 

#### Metodo Send() 
O método send devolve um Future, um Future é alguma coisa que vai executar daqui a pouco, então quer dizer, o send não é blocante, ele não segura, ele não é síncrono, ele é assíncrono.

#### Metodo Send().get()
Então, se eu quiser esperar ele terminar, eu vou chamar um get, que daí o get você espera... aqui você espera o Future terminar, aqui a gente está esperando e aí, pode dar uma exception, porque quanto você está esperando, alguém pode interromper ou enquanto você está esperando, pode dar um erro na execução.

Então, dá algumas exceptions possíveis. 

Vamos tentar rodar de novo, vou tentar rodar agora uma terceira vê e a gente vê que na terceira vez foi... Não deu ainda uma mensagem de sucesso ou de falha, por que não? 

Porque a gente não colocou nenhuma mensagem de sucesso ou falha.

O que a gente gostaria é: “A medida que eu envio, eu gostaria de ser notificado, se deu sucesso, se deu falha, o que que aconteceu”, repara, o get, até vai devolver alguma coisa para a gente, mas o que eu queria saber exatamente era, quando em paralelo acontecer alguma coisa, eu gostaria de ser notificado.

#### callback do send()
Então, eu quero passar um callback para o Kafka, para o Kafka que está enviando, para o produtor de mensagem. 

Então, o send tem uma variação que recebe um callback.

Então, basta a gente implementar essa interface callback, que tem um método, onCompletion, que recebe os metadados de sucesso ou a exception de falha, então a gente vai passar um único callback aqui. 

O callback vai receber os dados de sucesso ou a exception de falha, um dos dois.

E o que que a gente vai fazer? 

Vamos mostrar o que que aconteceu, por exemplo, se a exception for diferente de nulo, é porque deu erro, então vamos imprimir ela e vamos parar por aqui, se a exception é nula, então é porque deu sucesso.

Então, sout, vamos imprimir aqui no tópico data.topic, a gente colocou essa mensagem, ele colocou, por exemplo, na partição tal, no offset tal, em que posição que ele colocou e no timestamp, em que momento, no timestamp tal.

Então, aqui a gente tem o tópico, a gente tem a partição, eu fiz de uma maneira super simples a interpolação aqui, no offset tal e por fim, no timestamp tal, então isso daqui é o que foi enviado, então esse daqui é um sucesso, “sucesso enviando” nesse tópico.

Então aqui, eu estou colocando uma observer, para saber: “Quando em paralelo terminar, me avisa”, vou executar de novo, quarta vez que eu estou executando, recebo aqui toda a informação de log e opa, sucesso enviando para esse tópico, na partição 0, no offset 1, com esse timestamp.

Então, aqueles dois primeiros, a gente nem esperou terminar, os dois primeiros a gente nem esperou. 

Na terceira mensagem que eu mandei, eu esperei terminar, na quarta mensagem que eu mandei, eu esperei terminar.

#### offset
Por isso que o meu offset é 1, porque eu tive duas mensagens, a mensagem 0 e a mensagem 1, que foram a terceira e quarta, que eu esperei realmente serem enviadas a mensagem. Então, eu estou no offset 1 agora. 

Então, enviei algumas mensagens, sim, está funcionando.

Se eu rodar de novo, eu espero que esse offset cresça 1, por quê? Porque eu estou mandando mais uma mensagem, offset 2. 

Vamos agora no terminal, lembra? 

A gente tem aqui alguns consumers, a gente tinha aquele produtor simples de mensagem num tópico que não vamos usar mais, não estou usando ele.

E aí, a gente tinha dois consumidores, um consumidor, que estava consumindo loja novo pedido, que a agora vai consumir, qual que é o tópico qu e a gente usa?

ECOMMERCE_NEW_ORDER, from beginning, porque senão não tem graça, vamos dar uma olhada, vai consumir. 

Olha o que consumiu, três vezes.

Lembra? 

A terceira, a quarta e a quinta mensagem que eu mandei agora, o 0, 1 e o 2. 

Então, ele consumiu o 0, consumiu o 1, consumiu o 2. 

Vou parar aquela outra que a gente não vai mais usar também, então a gente só está usando esse consumidor agora.

Então, quer dizer, produzir mensagens se resume a isso, resume o quê? Criar um produtor, criar mensagens, enviar e colocar algum listener, então a gente fica escutando, quando a mensagem foi sucesso, eu sei que ela realmente foi, enquanto não for sucesso, eu não sei se ela realmente foi.

Então esse é o meu enviador de novo pedido. 

Tive um novo pedido, enviei a mensagem, a questão agora é, quem está escutando essa mensagem? 

Quem são os consumidores que estão escutando essa mensagem? 

Pom.xml
```
<dependencies>
        <!-- https://mvnrepository.com/artifact/org.apache.kafka/kafka-clients -->
        <dependency>
            <groupId>org.apache.kafka</groupId>
            <artifactId>kafka-clients</artifactId>
            <version>3.2.1</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.slf4j/slf4j-simple -->
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-simple</artifactId>
            <version>2.0.0</version>
        </dependency>

        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>2.0.0</version>
        </dependency>


    </dependencies>
    ```

    NewOrderMain.java
    ```
    public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var producer = new KafkaProducer<String, String>(properties()); //<String,String> é a chave,valor, sendo o valor o tipo da mensagem

        var value = "12345, 6789, 1209";
        var record = new ProducerRecord<String, String>("ECOMMERCE_NEW_ORDER", value, value); // parametros: topico, chave, mensagem

        // producer.send(record); // envia a mensagem assincrona
        // producer.send(record).get(); // envia a mensagem sincrona (espera a resposta de recebimento)
        producer.send(record, (data, ex) -> {
            if(ex != null) {
                ex.printStackTrace();
                return;
            }
            System.out.println("sucesso enviando nesse topico: " + data.topic() + "::: partition " + data.partition() + "/ offset" + data.offset()+ "/ timestamp" + data.timestamp());
        }).get(); // envia a mensagem sincrona com callback (lambda)
    }

    private static Properties properties() {
        var properties = new Properties();

        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092"); // ip e porta do kafka
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName()); // nome da classe de deserializaçao da chave
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName()); // nome da classe de deserializaçao da mensagem
        return properties;
    }
}
``` 

#### Criando consumidores em Java
Quando no meu sistema entra um pedido novo de compra, eu tenho várias coisas que podem acontecer, eu posso ter vários consumidores fazendo diversas coisas, no meu caso, eu vou ter um consumidor que detecta se é uma fraude ou não.

Então, eu quero criar um serviço que detecta se é fraude ou se não é fraude, o que está acontecendo. 

Isso é, eu quero criar aqui, aqui dentro, uma nova classe, que é um FraudDetectorService, que é um serviço, que vai ter várias coisas lá dentro, detecta se é uma fraude ou se não é uma fraude o que está acontecendo aqui.

Então, eu também vou ter uma função aqui, um método main estático e o que eu vou fazer agora que é criar um consumidor. 

##### Consumer
Então, eu vou criar um consumer, que é um new kafka consumidor. 

Lembrando, a chave vai ser uma string e o value também é uma string.

Então, crio isso aqui e ele recebe o quê? 

As propriedades de um consumidor, igual a gente tinha feito antes, criar aqui o properties, o que que eu vou fazer? 

Vou criar o meu properties, var properties, igual a new properties, retornar esses properties e configurá-los, certo?

Por padrão, o que que os meus properties vão ter?  

Primeiro, ConsumerConfig, não mais ProducerConfig. 

O servidor bootstrap servers config, da onde que ele vai escutar, ele vai escutar no 127.0.0.1:9092 ou vai pelo menos tentar começar a conectar com lá. 

A gente vai querer falar para ele, por exemplo, qual que é o deserializador, antes a gente serializou se string para bytes, agora a gente está de bytes para transformar em string.

Então as chaves vão ser deserializadas através do StringDeserializer.class.getName. 

Então, esse aqui é o deserializador da chave e do valor, mesma coisa, é o deserializador de string. 

Então, o que que o meu consumidor vai fazer? 

O consumidor vai falar o seguinte: “Eu gostaria de consumir uma mensagem, eu gostaria de consumir mensagens de algum lugar”, consumer.subscribe, em algum tópico.

Eu preciso falar qual é o tópico que eu quero subscrever e aí, você passa uma lista, uma colections. 

Eu vou passar uma singletonList, que é uma forma fácil de criar uma lista e na minha lista é uma ECOMMERCE_NEW_ORDER, esse é o tópico que eu estou escutando.

Você poderia escutar por mais de um? 

Poderia, é comum? 

Não. 

Por quê? 

Porque fica uma bagunça alguém escutando de vários tópicos, é super raro a gente escutando de vários tópicos, é muito raro, por quê? 

Porque cada serviço vai ter uma tarefa, um objetivo específico.

Se é um objetivo específico, vai estar escutando provavelmente um tópico específico, provavelmente, é raro escutar mais de um tópico. 

O que que eu quero fazer agora? 

Pergunta se tem mensagem aí dentro, “Consumer, pergunta se tem mensagem aí dentro”, por algum tempo.

Eu tenho que perguntar por algum tempo, um Duration.ofMillis, milissegundos, cem milissegundos para mim é o suficiente. 

Então, isso daqui vai me devolver o quê? 

Vários registros, quem são esses registros? 

Os registros que a gente enviou. 

Se os registros estão vazios, se eles estão vazios, então não tem nada, sysout “Não encontrei registros”, agora return. 

Se eu encontrei registros, eu quero fazer um for, var record e records, para cada um dos registros, eu quero fazer alguma coisa.

Imprimir alguma coisa, então “Processando new order” e aí, o dado da ordem que a gente vai colocar daqui a pouquinho é “... new order checking for fraud”, então, checando por uma fraude, o que que a gente vai colocar?

Vamos colocar uma informação, o record tem várias informações, por exemplo, a chave; por exemplo, o valor da mensagem, então esses dois valores; por exemplo, a partição onde foi enviada e por exemplo, um offset dessa mensagem, são as quatro mensagem que a gente tem.

Eu vou colocar só um sout aqui mais bonitinho com um monte de hífen, só para a gente ver bem claro: “Começamos uma mensagem nova”, que a gente está parseando e aí, a gente sai parseando cada uma dessas mensagens.

Para simular alguma coisinha lerda ou fingir um processamento de fraude, eu vou colocar um Thread.sleep, vou dormir cinco segundos entre em um outro, entre um record e outro. Vou colocar um try catch e vou ignorar esse catch, pois a gente não está fazendo nada com esse sleep, só para demorar aqui.

E a gente vai falar aqui, um sout, a order foi processada, com sucesso, sem sucesso, etc. 

Então, dessa maneira a gente processa a nossa order, então a gente quer ver agora rodar uma vez e ver isso acontecer. 

Lembra? 

O nosso offset, a gente mandou quantas mensagens para lá?

A gente já mandou três. 

Eu vou tentar rodar pela primeira vez esse fraud detector service e ver o que acontece. 

Rodou, exception, o que que ele falou? 

Para você rodar, você precisa de um grupo, calma aí, como assim, um grupo?

“Você não falou nada de grupo lá atrás, Guilherme”, acontece que é o seguinte, eu posso ter detector de fraude rodando e o detector de fraude quer consumir todas as mensagens, mas eu posso também ter um e-mail: “Olha, a sua compra está sendo processada”, que eu também quero rodar ou eu posso ter um sistema de Analytics, que analisa e fala: “Tive mais um pedido”.

Então, eu posso ter vários lugares, várias coisas escutando essa mensagem, escutando esse tópico e eu quero que cada um deles recebessem todas as mensagens.

Então, o fraud detector services, tem que receber todas as mensagens, o log service, tem que receber todas as mensagem, o outra coisa service, tem que receber todas as mensagens, cada um deles tem que receber todas as mensagens, então cada um deles é um grupo diferente.

#### Consumer Group
Então, quando a gente criar um consumer, a gente precisa dizer qual que é o grupo, o ID do grupo. 

E o ID do nosso grupo vai ser o FraudDetectorService.class.getSimpleName, para ficar mais simples o grupo ID, só FraudFetectorService. 

Então, o meu serviço vai ter esse grupo, que se chama FraudFetectorService.

#### Rebalancing entre o mesmo Consumer Group
Então, o fraud detector service vai receber todas as mensagens, todas as mensagens, se eu tiver um outro serviço, que tem outro grupo, ele vai receber também todas as mensagens, mas se dois serviços tem o mesmo grupo, as mensagens, vão “metade, metade”, não é exatamente “metade, metade”, mas elas serão distribuídas entre esses dois serviços que estão escutando através do mesmo grupo.

Então um grupo vai escutar todas as mensagens, mas se você tiver vários serviços rodando no mesmo grupo, você não sabe qual deles vai receber quais mensagens. 

No final serão processadas todas, mas você não sabe qual vai receber qual, então, essa é a sacada do grupo.

Então, a gente está criando um grupo aqui para a gente, vamos tentar rodar de novo. 

e agora, opa, “Não encontrei registros”, lembra? 

Ele está naquele padrão, que ele tenta pegar as mensagens novas e não encontra registros.

Então, o que que a gente quer fazer? A gente quer continuar escutando, eu quero deixar esse serviço rodando, rodando, rodando, por quê? 

Porque quando vier novas mensagens, ele tem que trabalhar.

#### poll
Então o normal, bem costumeiro mesmo, é a gente colocar a chamada do poll, essa chamada do poll, num laço, while true ou while alguma outra coisa, você pode ter algum sinalizador de quando tem que ser destruído o serviço, mil maneiras de fazer isso, a gente vai deixar aqui num while true por enquanto.

Então, eu vou rodar de novo e ele vai ficar escutando. 

Então, ó: “Não encontrei”, “Não encontrei”, “Não encontrei”, agora está imprimindo demais o não encontrei, né? Então eu vou fazer assim, quando ele encontrar, ele fala para a gente: “Encontrei tantos registros”.

Então, se ele encontrar, ele imprime quantos ele encontrou e aí, isso aqui, a gente pode mover para dentro do if.

Então, se não estiver vazio, ele vai mostrar para a gente que ele encontrou, se eu rodar aqui, olha, uma nova ordem de compra, vou rodar, então ele roda aqui separado uma nova ordem de compra.

A gente olha lá na fraud, olha aqui a chave, o valor, o 0, o 3 que é o offset e a ordem foi processada, se eu mudar os valores de produção, o valor agora é 1234 e rodar de novo, estou rodando agora uma nova ordem, 1234, vamos ver o fraud detector service, recebeu outra mensagem?

O fraud detector service recebeu a outra mensagem. 

Com isso a gente tem o nosso produtor e o nosso consumidor.

FraudDetectorService.java
```
public class FraudDetectorService {
    public static void main(String[] args) {
        var consumer = new KafkaConsumer<String, String>(properties());

        consumer.subscribe(Collections.singletonList("ECOMMERCE_NEW_ORDER")); // inscriçao nos topicos ouvidos

        while (true) { // fica chamando o kafka para procurar mensagens

            var records = consumer.poll(Duration.ofMillis(100)); // consulta o kafka por mais mensagens

            if (records.isEmpty()) {
                System.out.println("encontrei " + records.count() + " registros");
                continue;
            }
            for (var record : records) {
                System.out.println("-----------------");
                System.out.println("Processando new order, checking for fraud");
                System.out.println(record.key());
                System.out.println(record.value());
                System.out.println(record.partition());
                System.out.println(record.offset());

                try {
                    // simular um serviço demorado
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    // ignoring
                    throw new RuntimeException(e);
                }
                System.out.println("Order processed");
            }
        }
    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());// deserializador da chave
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()); // deserializador de mensagens
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, FraudDetectorService.class.getSimpleName());// consumer group name

        return properties;
    }
}

```

### O que aprendemos?
* O que são produtores
* O que são consumidores
* Criação de tópicos manualmente
* Como instalar e rodar o Kafka

## Paralelisando tarefas em um serviço
### Vários consumidores e produtores
Vamos criar, então, agora mais um produtor e mais um consumidor, isso é, o que eu gostaria de fazer agora era, na verdade, no momento que eu tenho uma nova ordem, despachar uma mensagem de nova ordem, eu também gostaria de despachar uma mensagem de por exemplo, um e-mail.

Então, eu gostaria de passar de repente duas mensagens, não só uma nova ordem, mas também um e-mail. 

Então, além desse record aqui, que é uma string, string, que eu estou enviando, eu queria enviar mais um, eu queria enviar um segundo record, que é um e-mail baseado nessa compra.

Então, o que eu vou fazer é literalmente falar para o meu produtor, enviar um novo record, esse daqui vai ser um record baseado... lembra que o record é um producer record, então ele vai mandar o meu e-mail e o mesmo listener. 

Calma aí, se eu quero mesmo que o listener que imprime as mensagens e etc., eu posso extrair ele para alguma coisa.

Eu primeiro vou extrair uma variável, vou extrair uma variável aqui para a gente, que eu vou chamar de callback. Então com esse callback, eu preciso de um e-mail record aqui também, e-mail record, producer record e o tópico é e-commerce, send e-mail, aqui eu estou pedindo realmente para enviar um e-mail.

E eu vou passar o valor do e-mail, tanto a chave do e-mail, quanto o valor do meu e-mail, lembra, por enquanto a gente não está usando a chave, mas eu prefiro passar o parâmetro da chave, então o que eu vou fazer é passar o mesmo valor, tanto para uma chave, quanto para o meu e-mail, vou passar, por exemplo, o mesmo valor.

Então, o que vou ter aqui é um var e-mail, “Thak you for your order!”, pronto, repetido e bola para frente.

Então, a gente está processando essa sua compra, então se eu rodar ela, vai enviar uma mensagem, que é através do tópico ECOMMERCE_NEW_ORDER e uma través do ECOMMERCE_SEND_EMAIL. 

Claro, além do fraud detector service, eu vou criar então agora um outro, que é o meu e-mail service.

Eu dou um copy, paste, EmailService, claro, depois a gente vai extrair isso daqui para ficar sem repetir código, a gente vai dar um subscribe em qual tópico? 

ECOMMERCE_SEND_EMAIL.

Olha os cuidados, send e-mail, recebe na strings, etc., a gente está fazendo o poll e encontrando, só que invés que processem o new order, é send e-mail e a gente está enviando esse e-mail. 

Eu vou esperar aqui só um segundo, invés de cinco e aí, eu vou falar que o e-mail foi enviado.

Então aqui, a gente está enviando o e-mail. 

Cuidado, o grupo ID é o ID do e-mail service, como a gente deu um copy paste, ele já trocou esse valor para a gente, para e-mail service. 

Então, eu tenho dois grupos agora, o grupo da fraude e o grupo do e-mail, dois grupos diferentes.

E aí, a gente está aqui escutando os vários e-mails. Então, o que a gente pode fazer? 

Vamos rodar tudo, então, repara que eu já tenho aqui alguma coisa rodando, vou dar stop, não vou querer deixar nada rodando.

Eu vou rodar, tanto o nosso e-mail service, e o nosso fraud detector service e com os dois rodando, eu vou mandar um pedido de compra, mandei o pedido e compra com o mesmo callback.

Então a gente vê, olha, enviou o new order e envio o send e-mail. 

No fraud detector teve a nova order e o no e-mail, recebeu o e-mail. 

Então, repare que agora, criar novos serviços, consumidores e criar novos pontos de produção, basta se reutilizar o que a gente está fazendo, mas mais interessante seria agora a gente tentar misturar tudo isso.

Vou criar um log, um serviço de log, então da mesma maneira tem esse e-mail service, eu vou querer um log service. 

O log service vai ser um caso mais genérico, ao invés de ele escutar o tópico ECOMMERCE_SEND_EMAIL, ele vai querer escutar diversos tópicos, Pattern.compile, que segue uma expressão regular, quem seguem a expressão regular que é a seguinte “ECOMMERCE*”, qualquer coisa que começa com ECOMMERCE.

Só que é uma expressão regular, expressão regular, para quem ainda não conhece, ponto quer dizer qualquer coisa. 

Então, e-commerce qualquer coisa. 

esse é o tópico que eu vou dar subscribe. 

Agora, como é log, então aqui encontrei tantos registros e invés de send e-mail, aqui é só um log, não vou esperar nada, vou sair imprimindo, não estou simulando que foi enviado um e-mail, não estou fazendo nada, estou logando e vendo o log acontecer.

Então, repare que agora eu vou ter três serviços rodando, aqui em baixo, esse é o serviço de log, está no grupo ID log, vou rodar. 

Então, eu tenho aqui três, o e-mail service está rodando, o fraud detector service está rodando e o log service está rodando.

Quando eu enviar uma mensagem com o nosso new order main, o que que vai acontecer?

Ele dispara duas mensagens e essas mensagens são consumidas por quem? 

A de e-mail, pelo e-mail service, a de ordem, pelo fraud service e ambas são consumidas pelo log service, ambas estão aqui.

Nesse nosso caso do log, é legal a gente imprimir o tópico agora, por quê? 

Porque o tópico agora pode ser qualquer um, então a gente pode imprimir inclusive o record.topic. 

Então, vou rodar novamente, a gente vai ver o log com os tópicos distintos.

Eu vou rodar de novo agora o new order main, então vamos rodar ele vai disparar as duas mensagens e o log service tem que consumir ambas. 

Lembra que eu falei que é muito raro a gente escutar mais de um tópico? 

É muito raro, aqui é um de log genérico, só para a gente ver todas as mensagens que estão sendo enviadas de um lado para o outro.

Olha, chegou uma do e-commerce new order, chegou uma do e-commerce send e-mail. 

Então, com isso, a gente é capaz de criar quantos grupos consumidores a gente quiser, cada um deles vai receber todas as mensagens daquele tópico a partir de agora.

E isso que a gente fez até aqui, a gente vai avançar e vai fazer coisas mais legais também.

NewOrderMain.java
```
public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var producer = new KafkaProducer<String, String>(properties()); //<String,String> é a chave,valor, sendo o valor o tipo da mensagem

        var value = "12345, 6789, 1209";
        var record = new ProducerRecord<String, String>("ECOMMERCE_NEW_ORDER", value, value); // parametros: topico, chave, mensagem

        Callback callback = (data, ex) -> {
            if (ex != null) {
                ex.printStackTrace();
                return;
            }
            System.out.println("sucesso enviando nesse topico: " + data.topic() + "::: partition " + data.partition() + "/ offset" + data.offset() + "/ timestamp" + data.timestamp());
        };

        var email = "thank you for your order! we are processing your order!";
        var emailRecord = new ProducerRecord<>("ECOMMERCE_SEND_EMAIL", email, email);

        // producer.send(record); // envia a mensagem assincrona
        // producer.send(record).get(); // envia a mensagem sincrona (espera a resposta de recebimento)
        producer.send(record, callback).get(); // envia a mensagem sincrona com callback (lambda)
        //segunda mensagem
        producer.send(emailRecord, callback).get();// envia a mensagem sincrona com callback (lambda)
    }

    private static Properties properties() {
        var properties = new Properties();

        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092"); // ip e porta do kafka
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName()); // nome da classe de deserializaçao da chave
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName()); // nome da classe de deserializaçao da mensagem
        return properties;
    }
}
```

LogService.java
```
public class LogService {
    public static void main(String[] args) {
        var consumer = new KafkaConsumer<String, String>(properties());

        consumer.subscribe(Pattern.compile("ECOMMERCE.*")); // inscriçao nos topicos ouvidos (todos os que começam com ECOMMERCE

        while (true) { // fica chamando o kafka para procurar mensagens

            var records = consumer.poll(Duration.ofMillis(100)); // consulta o kafka por mais mensagens

            if (!records.isEmpty()) {
                System.out.println("encontrei " + records.count() + " registros");

                for (var record : records) {
                    System.out.println("-----------------");
                    System.out.println("LOG");
                    System.out.println(record.topic());
                    System.out.println(record.key());
                    System.out.println(record.value());
                    System.out.println(record.partition());
                    System.out.println(record.offset());
                }
            }
        }
    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());// deserializador da chave
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()); // deserializador de mensagens
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, LogService.class.getSimpleName());// consumer group name

        return properties;
    }
}
```

EmailService.java
```
public class EmailService {
    public static void main(String[] args) {
        var consumer = new KafkaConsumer<String, String>(properties());

        consumer.subscribe(Collections.singletonList("ECOMMERCE_SEND_EMAIL")); // inscriçao nos topicos ouvidos

        while (true) { // fica chamando o kafka para procurar mensagens

            var records = consumer.poll(Duration.ofMillis(100)); // consulta o kafka por mais mensagens

            if (!records.isEmpty()) {
                System.out.println("encontrei " + records.count() + " registros");

                for (var record : records) {
                    System.out.println("-----------------");
                    System.out.println("sending email");
                    System.out.println(record.key());
                    System.out.println(record.value());
                    System.out.println(record.partition());
                    System.out.println(record.offset());

                    try {
                        // simular um serviço demorado
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // ignoring
                        throw new RuntimeException(e);
                    }
                    System.out.println("email sent");
                }
            }
        }
    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());// deserializador da chave
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()); // deserializador de mensagens
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, EmailService.class.getSimpleName());// consumer group name

        return properties;
    }
}
```

FraudDetectorService.java
```
public class FraudDetectorService {
    public static void main(String[] args) {
        var consumer = new KafkaConsumer<String, String>(properties());

        consumer.subscribe(Collections.singletonList("ECOMMERCE_NEW_ORDER")); // inscriçao nos topicos ouvidos

        while (true) { // fica chamando o kafka para procurar mensagens

            var records = consumer.poll(Duration.ofMillis(100)); // consulta o kafka por mais mensagens

            if (!records.isEmpty()) {
                System.out.println("encontrei " + records.count() + " registros");
                for (var record : records) {
                    System.out.println("-----------------");
                    System.out.println("Processando new order, checking for fraud");
                    System.out.println(record.key());
                    System.out.println(record.value());
                    System.out.println(record.partition());
                    System.out.println(record.offset());

                    try {
                        // simular um serviço demorado
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        // ignoring
                        throw new RuntimeException(e);
                    }
                    System.out.println("Order processed");
                }
            }
        }
    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());// deserializador da chave
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()); // deserializador de mensagens
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, FraudDetectorService.class.getSimpleName());// consumer group name

        return properties;
    }
}
```

### Paralelizando e a importância das keys
Até agora, a gente atacou o nosso problema com um consumidor e um produtor, isso é, quando tem uma pessoa produzindo, uma pessoa consome, mas até posso ter, por exemplo, o fraud service consumindo uma mensagem do tipo novo pedido, mas também ter o log service consumindo.

E como que a gente fez para que os dois recebessem todas as mensagens?

A gente fez com que eles tivessem grupos de consumo diferente. 

Então, quando eu tenho um grupo de consumo, eu vou consumir todas as mensagens, se você tem outro grupo de consumo, você também vai consumir todas as mensagens, funciona.

Só que o problema é o fraud service, a gente já viu, o processo de detectar fraude é um processo lerdo, é um algoritmo lento, etc. 

Então, você não quer rodar ele só uma vez, você quer deixar vários deles rodando ao mesmo.

Então, eu queria deixar rodando dois fraud detector service ao mesmo tempo. 

para fazer isso, vou parar todo mundo, e Rodar o fraud detector service duas vezes.

Então, agora ele tem o fraud detector service 1 e o fraud detector service rodando.

Aí, você fala: “Legal, Guilherme, então roda agora o new o order main”, vamos ver que que acontece? 

Vou rodar o new order main, mandei rodar, o que que o new order main faz? 

Envia uma mensagem para o new order, quem recebeu? 

O fraud detector service, será que o detector service 1, a segunda rodagem dele também recebeu?

Não, ainda bem, por quê? 

Porque **dentro de um grupo, quando chega uma mensagem com o tópico definido, ele vai chegar só em um desses caras, não vai chegar em todos**, por quê? Porque eu **não quero executar duas vezes o mesmo código para aquela mensagem**.

Então, se mensagem chegar, ela vai ser enviada para todos os grupos que estão escutando aquele tópico, mas dentro de um grupo, se eu tenho vários programas escutando aquele tópico, no mesmo grupo, só vai para um deles, só foi para um, que foi esse daqui.

Vou tentar de novo, vou rodar de novo, para quem será que vai agora? 

Foi para o mesmo, para quem será que vai agora? 

Essa foi a 9, agora foi a 10, foi para o mesmo, para quem será dado agora?

Agora foi a 11, foi para o mesmo, “Guilherme, legal, você paralelizou, você vai ser capaz de executar duas detecções defraudes ao mesmo tempo, na mesma máquina, em máquinas diferentes, etc., maravilha, só que está caindo sempre no mesmo, por que que está caindo sempre no mesmo?

#### Paralelismo no kafka (rebalance)
A questão é, como que o Kafka paraleliza isso? 

Como é que ele divide que dentro de um grupo, esse consumidor vai receber essas mensagens e esse, essas outras mensagens? 

#### Partiçoes (partitions)
Isso é feito através das partições.

Então, se a gente der uma olhada na configuração do nosso servidor, então esse consumer aqui, que a gente tinha rodando, posso parar. 

Se a gente der uma olhada no server.properties, a gente vai ver que lá, quando fala de partitions, número de partições, uma, isso quer dizer o quê?

Quer dizer que para cada tópico por padrão, eu só tenho uma sequência de mensagens e toda a mensagem cai nessa sequência, nessa partição, como só tem uma parte e essa parte tem tudo, então está todo mundo lá, o que que acontece?

Quando a gente levanta um consumidor, esse consumidor se responsabiliza por várias partes, como só tem uma parte, ele se responsabiliza pela única parte e foi isso o que aconteceu. 

Esse cara aqui, ele é responsável pela única partição a partição 0.

Se a gente olhar no log, você vai ver no comecinho, ele falando: Eu estou responsável pela partição 0”, calma aí, se ele está responsável por todas as mensagens dessa partição, esse outro cara, na hora que ele perguntar: “Tem uma partição aí para mim?”.

Você vai olhar o que ele vai falar: “Não juntei, não estou em nenhuma partição, porque só tem uma partição”, se dois consumissem da mesma partição, os dois iam receber as mesmas mensagens e a gente não quer, a gente quer dividir entre os dois.

Então, não devemos ter mais consumer no mesmo grupo, do que o número de partições, por quê? 

Porque que um deles vai ficar parado, olhando para o teto sem fazer nada. 

Então, o que que a gente tem que fazer? 

A gente tem que reparticionar o nosso tópico, a gente tem que rebalancear tudo isso daí.

Então, como é que a gente pode rebalancear? 

Como é que a gente pode, primeiro, reparticionar, para a gente ter, por exemplo, duas partições? 

A gente tem várias maneiras de fazer isso, uma é trocar aqui a configuração para padrão, para que em novos tópicos, eu vou querer ter três partições, em novos tópicos, isso é muito importante.

Então, se eu salvar e sair, os tópicos existentes ECOMMERCE_NEW_ORDER, ele tem só uma partição, já foi criado, então não tem três partições.

O que que eu quero fazer? 

#### alterar um topico ja existente pela linha de comando (kafka-topics.bat --alter)
Eu quero alterar o tópico, e para alterar o tópico, tem linha de comando para a gente fazer isso, bin/kafka-topics.sh, eu quero alterar, eu vou falar onde está o zookeeper, localhost:2181, eu vou falar o tópico, qual que é o tópico mesmo?

É esse tópico aqui, ECOMMERCE_NEW_ORDER, esse é o meu tópico e o que que eu vou querer falar? 

Eu vou falar: “Para esse tópico, eu queria três partições", por exemplo, podia ser quatro, podia ser 12, número de partições que fizessem sentido para a gente paralelizar.

```
bin/windows/kafka-topics.bat --bootstrap-server localhost:9092 --describe
```

Lembrando, o número máximo de paralelização vai ser o número de partição, então aqui eu vou colocar três partições, vamos ver o que acontece, ele está reparticionando, adicionou as partições com sucesso. 

Vamos rodar aquele comando de describe de novo?

```
bin/windows/kafka-topics.bat --alter --bootstrap-server localhost:9092 --topic ECOMMERCE_NEW_ORDER --partitions 3
```

Vamos ver o que ele fala, ele fala: “Legal, para o ECOMMERCE_NEW_ORDER, eu tenho agora três partições, a partição 0, a partição 1 e a partição 2, então te, três partições agora, agora não tem mais só uma, tem três partições. 

Se eu tenho três partições, o que que deveria acontecer?

Cada um desses serviços aqui, cada um desses serviços deveria pegar partições distintas, eu vou dar um stop aqui e um stop no outro... dar um stop nesse e um stop nesse outro e vou executá-los novamente.

Então, eu vou executar aqui nas configurações o fraud detector service 1, estou começando do 0, fraud detector service 1 e o fraud detector service. 

Quando eu rodei o 1, ele já falou aqui para a gente: “Olha, eu peguei as partições 2,1 e 0”, ele pegou todas, por quê?

Porque só tem ele consumindo, se só tem ele consumido nesse consumer group, ele tem que pegar todas as partições, para garantir que ele processe todas as mensagens. 

Na hora que a gente rodar agora o segundo, o que que vai acontecer?

Ele vai dividir, ele vai falar: “Olha, tudo bem, você tinha as três partições e você vai ficar com 0? 

Não tem graça, então vamos distribuir melhor, vamos rebalancear isso daí. 

Vamos ver o que ele decidiu aqui para a gente, ele falou: “Eu vou ser o responsável repartição 1 e 0”.

Se ele ficou responsável pela 1 e 0, esse daqui ficou responsável pela partição 2. 

Então, um deles ficou responsável por uma partição, o outro por duas, ele rebalanceou, na hora que você colocou um novo consumidor no consumer group, ele rebalanceou.

Não é necessariamente naquele instante, na hora que a gente faz um poll novo, a gente está dando uma chance para rebalancear, etc., tem várias questões de quando o rebalanciamento é feito, a gente não precisa entrar nos detalhes, mas o que a gente quer ver é a paralelização e isso acontece nesse instante, depois a gente vai entrando em mais detalhes.

Vamos tentar agora? 

Eu chego lá no meu new order main e executo uma vez, mandou uma mensagem, quem recebeu? 

Quem recebeu a mensagem?

Quem recebeu a mensagem foi o fraud detector service, ele recebeu na partição 0. 

Vou rodar outro, vamos ver para que partição que vai agora? 

O Kafka vai tentar distribuir e escolher a partição.

Vamos ver em quem caiu? 

Opa, caiu aqui de novo, vamos tentar de novo? 

em quem será que vai cair? 

Caiu por enquanto na partição 0, caiu na partição 0 de novo, está caindo sempre na partição 0, por que que está caindo sempre na partição 0?

#### como o kafka faz o rebalanceamento das mensagens (direcionamento)
O kafka precisa de algum algoritmo para decidir em qual dessas partições ele vai enviar, como que ele faz isso? 

Através de uma **chave**, e adivinha, a gente está mandando sempre a mesma chave, a chave é sempre ou o e-mail ou o próprio valor da mensagem, como a gente está mandando sempre a mesma chave, ele está caindo sempre na mesma partição.

Então, a chave é quem decide em qual partição vai cair, não é direto, “Ah, eu quero que caia na partição 0”, “Eu quero que caia na partição 1”, não, é uma chave.

A gente poderia falar aqui: “Olha, a chave que eu vou usar é o ID do usuário”, qual poderia ser o ID de um usuário? 

Vou criar um ID usuário aleatório, por exemplo, um UUID, vou pegar aqui um UUID aleatório, transformar numa string.

Então, toda a vez que a gente rodar, a gente vai ter um ID diferente, uma chave diferente, um ID diferente do usuário, então eu vou concatenar aqui, aqui mais isso daqui, esse daqui é o meu valor, é a minha mensagem e a gente vai usar isso como chave, tanto para o new order, quanto para o e-mail, para os dois a gente vai usar isso como chave.

Então, agora, cada vez que a gente roda, a gente está usando uma chave nova, por quê? 

Porque é o ID do usuário. 

Eu espero que os IDs do usuários, são aleatórios, então vai ser bem distribuído o hash dessas chaves.

Então, o que a gente vai fazer agora é rodar. 

Então, repara que quando a gente rodar o new order main, ele mandou para a partição 0. 

Mandei de novo. Mandou para a repartição um.

Aqui ele mandou para a 0, azar o dele, isso aqui não tem nada a ver, pode ser repartições diferentes, aliás, principalmente porque aqui só tem uma partição e aqui a gente tem três. Então, aqui, ele mandou agora para a partição 1.

Curiosamente, a partição 0 e a partição 1, estão nesse cara, vamos rodando até cair na partição 2? 

Vou rodar de novo, algum ID vai para partição 2, alguma hora, lembrando que a partição é 0, 1 e 2. 

Olha, foi para a partição 2. 

Quer dizer que se eu fizesse um for disso daqui, invés de enviar só uma vez, enviasse 100 pedidos, invés de fazer isso uma única vez, eu fizesse isso 100 vezes, então vou fazer um for, estou fazendo da maneira mais tosca, mais antigona com for simples e vou executar esse código aqui 100 vezes, o que que vai acontecer?

Vai ter mensagem indo para tudo que é lado, vamos rodar? 

estou rodando, está mandando as mensagens e esse aqui, como ele demora, ele vai processando.

Processou na partição 0, 17, agora na partição 1, o offset 2, quer dizer, a terceira mensagem da partição 1, agora a quarta mensagem da partição 1. 

Aqui, eu estou na quarta mensagem ou quinta, provavelmente, 0, 1, 2, 3, 4, 5 da partição 2, então eles estão executando, eles estão consumindo, cada um nas suas partições em paralelo.

Então o número de partições tem que ser maior ou igual ao número de consumidores dentro de um grupo, senão o consumidor dentro daquele grupo fica parado olhando para o teto e aqui, eu gerei muita coisa. 

#### kafka-consumer-groups (kafka-consumer-groups.bat --all-groups)
A gente também consegue um comando super legal e importante no dia-a-dia, é o “bin/kafka-consumer-groups.

```
.\bin\windows\kafka-consumer-groups.bat --all-groups --bootstrap-server localhost:9092 --describe
```

Eu quero analisar os grupos de consumo, a gente pode falar o bootstrap server, por exemplo, que é o nosso localhost:9092, um dos servidores que a gente tem, que é o único e falar para ele: “Descreve para mim como que estão os grupos de consumo, descreve”. 

Mostra aí para mim, eu quero saber todos os grupos, --all groups. 

Então, o que que ele mostra aqui para mim?

Ele vai mostrar: “O grupo e-mail service, que é o e-mail service, eu estou interessado no grupo Fraud detector service”, o grupo fraud detector service, olha, 1, 2, 3, tem três lugares aqui rodando. 

O tópico é: e-commerce new order, o fraud detector service escuta e-commerce new order, é verdade.

A partição, olha, esse daqui está responsável pela partição 0, esse pela partição 1, esse pela partição 2. 

Repara aqui, log e offset, quantas mensagens tem nessa partição? 

Nessa partição tem 43, nessa 40, nessa 36; current offset, quer dizer, esse nosso grupo está processando qual mensagem dessa partição?

Ele está na mensagem 18 de 43, isso é, faltam 25; esse está na mensagem 2 de 40, isso é, faltam 38; essa está na mensagem 3 de 36, isso é, faltam 33.

E de tempo em tempo, se eu rodar, como eu estou rodando lá o fraud detector service, ele está consumindo aos poucos, ele vai atualizar esses números para a gente, tem que esperar ele notificar o servidor, que ele está consumindo.

Então repara, ele está processando, aqui também, processando, processando e com isso, ele vai atualizar essas tabelas de offsets, de o quanto ele já consumiu de cada uma das partições.

Então, no final, a gente tem aqui um tópico que está dividido em várias partições, quando a mensagem chega, ela vai para uma dessas partições de acordo com a chave, ela vai para uma dessas partições.

A gente tem consumidores separados por grupo, todo o grupo vai receber todas as mensagens, mas dentro de um grupo, cada um dos consumidores vai acessar o número de partições, divido pelo número de consumidores.

Então, se eu tenho cinco partições e dois consumidores, uma vai acessar dois e outra vai acessar três partições. 

Aqui, no meu caso, a gente pode até ver, consumer ID, a gente tem um consumidor aqui, que é esse 7cb, consumindo essas duas partições e um outro consumidor consumindo essa partição.

#### Client ID no consumer
Inclusive, esse consumer ID, é um valor que você pode setar, você pode vim no seu fraud detector service e falar: “Eu quero dar um nome para o meu consumidor”, cada uma das vezes que eu rodo, eu vou querer um config.clientID.

O meu client ID vai ser tal, você pode colocar o IP da sua máquina, mas repara, se tiver dois rodando no mesmo IP, vai ficar bizarro, então o ideal é você dar um ID único aqui. Você poderia, por exemplo, colocar o simple name do seu serviço, mais um underline e um ID, UUID.randomUUID.toString.

é um ID único que eu estou gerando, que se assemelha um pouco ao que ele fez aqui, consumer 1, que ficou padrão e UUID que ele gerou, não sei se é UUID, mas um ID que ele gerou aqui para cada um desses dois.

A gente tem dois, um consumindo essas duas partições e essa é uma outra, se a gente rodar de novo, vamos ver se atualizou, se os consumidores já “comitaram”, já notificaram o Kafka de terem consumido todas as mensagens. 

Olha, esse aqui já notificou, inclusive notificou que já terminou 36 de 36.

Os outros ainda estão consumindo, e não notificou ainda que já processou diversas delas. 

A gente vai falar sobre esse commit, etc., com o passar do tempo. 

Então, com isso, a gente viu como paralelizar e qual é a sacada e importância da chave para paralelização.

Então, agora, ele terminou e ele notificou, ele fez um commit e na hora que ele tentou fazer um commit aqui, parece que deu algum erro, me parece. Vamos ver se deu algum erro ou não, dá uma olhada, aqui... Ele conseguiu “comitar” a primeira, ele falou: “Eu consumi do 36 em diante, mas na segunda partição deu algum erro, do 2 e o 40”, ele não conseguiu.

Na partição 1 e na 0, que são essas duas aqui, nessas duas partições deu algum erro, por quê? 

Porque no meio do caminho, enquanto a gente estava rodando aqui, enquanto a gente estava rodando, o nosso Kafka decidiu rebalancear.

Como ele decidiu rebalancear, o que que aconteceu? 

Foi tudo mudado, quem estava em qual partição, foi alterado, como quem estava em qual partição foi alterado, ele não conseguiu “comitar” as partições.

Ele até falou: “Dei a sorte de pegar o momento, que o fraud detector service estava rebalanceando as partições, de acordo com as partições”, e aí, ele não conseguiu “comitar” e o que que está acontecendo agora? 

Então agora, o que que ele tem aqui? “(RE-) Joining group”, está tentando rejuntar o grupo, enquanto isso, esse cara aqui está processando, esse aqui está processando, está processando. Esse cara aqui ainda está processando, mas ele não consegue ainda se comunicar aqui porque ele está fazendo o rebalanceamento.

Então, o que que é perigoso aqui? 

Se a gente ficar consumindo muita mensagem de uma vez só, quando a gente faz o commit, pode estar no meio de um rebalanceamento e aí, se perder completamente.

Então a gente gostaria de ter controle fino sobre o commit, tem várias maneiras de fazer isso.

NewOrderMain.java
```
public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var producer = new KafkaProducer<String, String>(properties()); //<String,String> é a chave,valor, sendo o valor o tipo da mensagem

        for(int i = 0; i < 100; i++) {
            var key = UUID.randomUUID().toString();
            var value = key + "- 12345, 6789, 1209";
            var record = new ProducerRecord<String, String>("ECOMMERCE_NEW_ORDER", key, value); // parametros: topico, chave, mensagem

            Callback callback = (data, ex) -> {
                if (ex != null) {
                    ex.printStackTrace();
                    return;
                }
                System.out.println("sucesso enviando nesse topico: " + data.topic() + "::: partition " + data.partition() + "/ offset" + data.offset() + "/ timestamp" + data.timestamp());
            };

            var email = "thank you for your order! we are processing your order!";
            var emailRecord = new ProducerRecord<>("ECOMMERCE_SEND_EMAIL", key, email);

            // producer.send(record); // envia a mensagem assincrona
            // producer.send(record).get(); // envia a mensagem sincrona (espera a resposta de recebimento)
            producer.send(record, callback).get(); // envia a mensagem sincrona com callback (lambda)
            //segunda mensagem
            producer.send(emailRecord, callback).get();// envia a mensagem sincrona com callback (lambda)
        }
    }

    private static Properties properties() {
        var properties = new Properties();

        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092"); // ip e porta do kafka
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName()); // nome da classe de deserializaçao da chave
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName()); // nome da classe de deserializaçao da mensagem
        return properties;
    }
}
```

FraudDetectorService.java
```
public class FraudDetectorService {
    public static void main(String[] args) {
        var consumer = new KafkaConsumer<String, String>(properties());

        consumer.subscribe(Collections.singletonList("ECOMMERCE_NEW_ORDER")); // inscriçao nos topicos ouvidos

        while (true) { // fica chamando o kafka para procurar mensagens

            var records = consumer.poll(Duration.ofMillis(100)); // consulta o kafka por mais mensagens

            if (!records.isEmpty()) {
                System.out.println("encontrei " + records.count() + " registros");
                for (var record : records) {
                    System.out.println("-----------------");
                    System.out.println("Processando new order, checking for fraud");
                    System.out.println(record.key());
                    System.out.println(record.value());
                    System.out.println(record.partition());
                    System.out.println(record.offset());

                    try {
                        // simular um serviço demorado
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        // ignoring
                        throw new RuntimeException(e);
                    }
                    System.out.println("Order processed");
                }
            }
        }
    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());// deserializador da chave
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()); // deserializador de mensagens
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, FraudDetectorService.class.getSimpleName());// consumer group name
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, FraudDetectorService.class.getSimpleName() + "_" + UUID.randomUUID().toString()); // ID unico de cada instancia
        return properties;
    }
}
```

### Max poll e dando mais chances para auto commit
A gente viu que quando eu estava rodando, ele decidiu rebalancear e isso fez com que, como ele rebalanceou e eu ainda estava rodando coisas aqui, relativas às mensagens que eu tinha consumindo, na hora que eu tentei notificar: “Olha, eu já consumi as mensagens que você me enviou”, ele falou: “Não, não, não, eu estou todo mudado, eu não estou que nem você estava esperando”.

E aí, eu não consegui “comitar” as mensagens, então a gente reparou que quando a gente foi lá na situação do consumer group, ele falou: “Está rebalanceando”.

Então, se a gente rodar o consumer group agora e der stop nos programas ele falou: “O fraud detector service não tem ninguém rodando”, é verdade, não tem ninguém rodando, se a gente for lá no log, a gente vai ver que o fraud detector service agora está vazio, não tem ninguém consumindo.

No Kafka também não tem mensagem... aquele era o Kafka, desculpa e no zookeeper, a gente vai tendo as mensagens de sempre, vai rodando lá. Então, o que que está faltando agora eu fazer?

repara que eu queria diminuir esse tempo do commit, eu queria de tempo em tempo avisar: “Tudo bem, já processei essas mensagens, porque se eu for processar aqui 100 mensagens, vai demorar e nesse meio tempo, alguma coisa pode acontecer, então eu gostaria de que esse commit fosse feito mais rápido, fosse feito... na hora que eu pego as mensagens, eu já queria mais rapidamente avisar: já consumi”.

#### Poll
**O poll é um instante onde acontece um commit**, é verdade, existem outros instantes onde acontece o commit, outras configurações que a gente vai ver mais para frente na medida que a gente avança na arquitetura do Kafka, no conjunto de serviços que a gente cria.

Nesse instante, o que eu gostaria de fazer era **acessar o poll mais frequentemente**, para que o rebelence, o rebalanceamento não influenciasse tanto esse consumo duas vezes da mesma mensagem.

#### Maximo de records consumidos
Então, o que eu queria fazer é adicionar uma outra propriedade, eu queria falar que nas configurações do meu cliente, o máximo de records que eu quero consumir por vez, no máximo 1.

De um em um, eu vou “auto-comitando”, eu vou dizendo: “Beleza, já terminei esse, já terminei aquele”, e etc., então eu quero no máximo de um em um. 

É uma configuração que muita gente usa, empresas grandes utilizam também, fazer o poll de um em um.

É claro, quanto maior é o poll que você faz, no máximo você vai receber aquele número de mensagens, então você pode fazer um transporte de banda meio que otimizado: “Envia várias, depois não faz nada”, aqui você vai ter de uma e uma, mas você tem chances maiores de ter menos conflitos, é isso que a gente está fazendo, é uma escolha.

Então, o que que eu vou fazer? 

Rodar o processo de sempre, a gente roda o fraud detector service 1, então vou rodar também o fraud detector service normal, assim a gente tem dois dele rodando e o primeiro já começou a consumir.

Opa, falou: “Vai ter um rebelence acontecendo”, falou: “Vai rolar um rebalence”, vamos dar uma olhadinha lá no consumer group? 

A gente vai conseguir ver, consumer groups, vamos ver como é que está o nosso grupo fraud detector service.

Ele tem os IDs já, que a gente gerou, o fraud detector service, esse aqui, o 066 e o 5b1, já tem dois diferentes, são os dois rodando, um está responsável por esses dois e outro responsável por esse, por essa partição.

Nessa partição aqui está tudo parado, não tem nada acontecendo, então só tem coisa acontecendo aqui, nesse está faltando 25, nesse está faltando 34, está faltando aqui 34 e 25, que são as colunas LAG, o quão atrasado a gente está.

Então, se a gente rodar agora de novo, ele foi consumindo de uma em uma, 25 e 27, então agora, a medida que ele for consumindo de uma em uma, foi dando a oportunidade de commits menores, commits menores, o commit chegou e eu estou feliz.

Então de tempo em tempo, ele está dando essa oportunidade desse commit. É claro, ele pode decidir daqui a pouco, por algum motivo, de fazer um novo rebalanceamento, diversos motivos podem (triguiar) um rebalanceamento.

E aí, ele começaria consumindo os dois caras de novo, se desse a sorte de um pegar esse, o outro aquele e etc., tem motivos aí, a gente viu como a gente pode gerar um rebalanceamento, por exemplo, reparticionando.

Então, essa é uma maneira de a gente configurar o máximo de records que a gente quer e super utilizado, para que a gente tenha mais oportunidades de não duplicar mensagens, de não executar duas vezes a mesma mensagem, porque falhou o commit.

FraudDetectorService.java
```
public class FraudDetectorService {
    public static void main(String[] args) {
        var consumer = new KafkaConsumer<String, String>(properties());

        consumer.subscribe(Collections.singletonList("ECOMMERCE_NEW_ORDER")); // inscriçao nos topicos ouvidos

        while (true) { // fica chamando o kafka para procurar mensagens

            var records = consumer.poll(Duration.ofMillis(100)); // consulta o kafka por mais mensagens

            if (!records.isEmpty()) {
                System.out.println("encontrei " + records.count() + " registros");
                for (var record : records) {
                    System.out.println("-----------------");
                    System.out.println("Processando new order, checking for fraud");
                    System.out.println(record.key());
                    System.out.println(record.value());
                    System.out.println(record.partition());
                    System.out.println(record.offset());

                    try {
                        // simular um serviço demorado
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        // ignoring
                        throw new RuntimeException(e);
                    }
                    System.out.println("Order processed");
                }
            }
        }
    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());// deserializador da chave
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()); // deserializador de mensagens
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, FraudDetectorService.class.getSimpleName());// consumer group name
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, FraudDetectorService.class.getSimpleName() + "_" + UUID.randomUUID().toString()); // ID unico de cada instancia
        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");// quantidade max de records recebidos no poll
        return properties;
    }
}
```

### O que aprendemos?
* Como rodar diversos consumidores no mesmo grupo
* Como paralelizar tarefas
* A importância da chave para hash
* Cuidado com poll longo

## Criando nossa camada
### Extraindo uma camada de consumidor (refactor)
Vamos, então, agora dar um tapa nesse código todo que a gente criou, lembra que eu fiz bastante copy e paste? 

Um primeiro copy paste foi nessa parte de consumir os códigos, consumir mensagens, todos os nossos services tem os properties e por aí, vai.

Vamos extrair isso, eu vou criar aqui uma classe chamada kafkaService, o kafka service é um serviço meu, então, o que eu vou mudar? 

Eu vou mudar o meu EmailService, para que ele pare de funcionar dessa maneira, o que eu vou querer é dentro do meu método main, fazer uma coisa muito mais simples.

Eu vou querer dar um new kafka service e quando eu crio o kafka service, eu já falo qual é o subject que eu quero escutar, ECOMMERCE_SEND_EMAIL.

vou falar também qual que é a função que eu vou executar para cada mensagem que eu recebo, então eu vou criar uma função aqui no meu e-mail service, que eu vou poder chamar.

eu vou criar uma função chamada tipo parse, algo do gênero, que vai ser chamada. 

E aí, o que mais que eu vou fazer? 

Vou falar propriedades extras que eu tenho interesse, se eu tiver interesse. 

Então isso daqui é o que eu vou querer fazer, esse daqui vai ser o meu service, é um var.

E aí, eu vou fazer um service.run. 

Claro, eu quero ter uma função parse, eu vou querer ter uma função private void parse, que vai receber um record, qual que é o tipo record? 

É um consumer record.

No nosso caso, a gente está trabalhando sempre com strings por enquanto, a chave é string, a gente descerializa e serializa como string e o valor é strings que a gente descerializa e descerializa como string.

Então, essa vai ser a minha função, essa daqui é o parse, vai ser para cada record.

o meu parse, que é o código que está ligado com o envio de e-mail. 

mas repara, o parse é uma função da minha classe e-mail service.

Então esse daqui var, esse daqui que é o meu e-mail service, eu vou criar um new e-mail service, então eu crio um new e-mail service e aí, eu vou falar e-mail service: parse. 

Claro, eu preciso criar esse construtor, que recebe o topic e Uma função de parse, que recebe o record e não devolve nada, eu vou chamar de ConsumerFunction, ela é uma consumer function.

Vou criar essa interface, consumer function, a interface consumer function é uma única função consume, que recebe um consumer record de strings para strings. 

Então é uma função, uma interface que pode ter uma única implementação de função, que recebe um record.

E é isso mesmo que a gente fez com o nosso parse, é bem uma função que recebe um record. 

Então, a gente está passando aqui uma referência para função, (Method References), Java e falando: “Eu quero que você invoque essa função para cada record”

Claro, eu vou criar o método run, que vai rodar para valer aqui a coisa. 

A gente vai criar o consumer, this., como variável membro, this.consumer, criei, final. 

Vou fazer um while, vou pegar os records,vou verificar se está vazio, se não está vazio, eu falo o que eu encontrei, quantos registros encontrei, que deveria ser um, porque a gente está com o max poll 1.

E aí, a gente vai fazer agora o record, para cada record que tem lá dentro, a gente chama o parse. 

Só que, o while, eu vou jogar no método run.

Então, do jeito que eu fiz a função parse, eu tenho que criar um campo para ela, um field, ele é final, então eu tenho o parse aqui para poder ser executado.

E o e-mail service está fazendo isso, está invocando e chamando lá e deixando feliz e contente, “Estou super feliz aí”, a questão é: “Será que esse código funciona?”, vamos testar. 

Vou dar stop aqui em todo mundo, vou rodar o e-mail service e aí, eu vou rodar o new order main uma única vez.

Então, ele está escutando, já tinha e-mail para ele processar, ele está processando um monte de e-mails, então realmente está funcionando.

como o tópico já existia no nosso consumer groups, com esse tópico, então ele continuou da onde ele estava, ele continua da onde ele estava.

Se o consumer group não existia, ele podia começar do final, porque ele não sabia que esse tópico existia, nem nada do gênero. 

está funcionando o nosso e-mail service, vamos migrar o fraud service também, o fraud detector service, para usar o e-mail service. 

Então, assim como esse cara está enviando os e-mails, eu queria que o fraud detector service usasse esse padrão novo.

Então eu crio um fraud detector service, eu vou chamar isso daqui de meu fraudService, aí eu crio o meu service do Kafka e o KafkaService, o KafkaService recebe o tópico, que é isso daqui.

Tudo o que é obrigatório, é passado no construtor, então antes do tópico, eu vou falar qual é o meu grupo, vai ser obrigado. 

Então, antes do tópico, eu vou falar: e-mail service class get simple name. 

Então aqui, eu posso só alterar o meu construtor, adicionar string como primeiro parâmetro.

Aí, ele vai dar uma sugestão de refatoração, de como fazer isso, colocar um valor padrão, um default value e aqui. 

A gente conseguiu extrair os serviços com um código bem mais simples. 

É claro, ele não está suporte aos Paterns, a gente faz depois, quando for o caso de trabalhar no log novamente.

O que eu quero refatorar agora e (extrair) é a mesma coisa para o produtor, eu não quero ficar trabalhando dessa maneira com o produtor, quero deixar bem mais simples, a gente vai fazer daqui a pouco.

KafkaService.java
```
class KafkaService {
    private final KafkaConsumer<String, String> consumer;
    private final ConsumerFunction parse;

    KafkaService(String groupID, String topic, ConsumerFunction parse) {
        this.parse = parse;
        this.consumer = new KafkaConsumer<String, String>(properties(groupID));
        consumer.subscribe(Collections.singletonList(topic)); // inscriçao nos topicos ouvidos
    }

    void run() {
        while (true) { // fica chamando o kafka para procurar mensagens
            var records = consumer.poll(Duration.ofMillis(100)); // consulta o kafka por mais mensagens

            if (!records.isEmpty()) {
                System.out.println("encontrei " + records.count() + " registros");
                for (var record : records) {
                    parse.consume(record);
                }
            }
        }
    }

    private static Properties properties(String groupID) {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());// deserializador da chave
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()); // deserializador de mensagens
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupID);// consumer group name
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());// client name

        return properties;
    }
}
```

ConsumerFunction.java
```
public interface ConsumerFunction {
    void consume(ConsumerRecord<String, String> record);
}
```

EmailService.java
```
public class EmailService {
    public static void main(String[] args) {
        var emailService = new EmailService();
        var service = new KafkaService(
                EmailService.class.getSimpleName(),
                "ECOMMERCE_SEND_EMAIL",
                emailService::parse
        );
        service.run();
    }

    private void parse(ConsumerRecord<String, String> record) {
        System.out.println("-----------------");
        System.out.println("sending email");
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println(record.partition());
        System.out.println(record.offset());

        try {
            // simular um serviço demorado
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // ignoring
            throw new RuntimeException(e);
        }
        System.out.println("email sent");
    }
}
```

FraudDetectorService.java
```
public class FraudDetectorService {
    public static void main(String[] args) {
        var fraudService = new FraudDetectorService();
        var service = new KafkaService(
                FraudDetectorService.class.getSimpleName(), // group
                "ECOMMERCE_NEW_ORDER", // topic
                fraudService::parse // parse function
        );
        service.run();
    }

    private void parse(ConsumerRecord<String, String> record) {
        System.out.println("-----------------");
        System.out.println("Processando new order, checking for fraud");
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println(record.partition());
        System.out.println(record.offset());

        try {
            // simular um serviço demorado
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // ignoring
            throw new RuntimeException(e);
        }
        System.out.println("Order processed");
    }
}
```

### Extraindo nossa camada de producer
Da mesma maneira que a gente extraiu o nosso Kafka service, eu quero extrair o Kafka producer, só que Kafka producer já é uma classe que existe, eu vou chamar de “KafkaDispatcher”, o Kafka dispatcher, a gente vai usar aqui, quando a gente quer gerar coisas.

Então, a primeira coisa que eu vou querer ter é um dispatcher, que vai ser o nosso Kafka dispatcher, então o nosso Kafka dispatcher tem que ter um construtor. 

Dentro desse construtor, ele vai criar o nosso producer, é esse cara aqui.

Precisa dos properties? 

Vamos tirar daqui e jogar para lá. 

Pensa, esses properties são genéricos ou são específicos? 

Dá uma olhada, olha bootstrap service, key e value, como a gente só está trabalhando com string por enquanto, ele é bem genérico. 

Depois, quando a gente trabalhar com outros tipos, aí ele é específico.

a gente criou o nosso dispatcher, o que que a gente vai fazer? 

Um for. 

Dentro desse for, a gente gostaria de enviar uma mensagem, então eu gostaria de simplesmente ir mais direto: “Eu tenho a minha chave e tenho o meu valor, eu quero enviar, eu quero falar dispatcher, send”, nesse tópico aqui, envia para mim essa chave e esse valor.

E aí, ele se vira com tudo isso daqui, tudo isso e isso vai embora, então vou chamar o send e ele vai usar o callback padrão que a gente criou, que é só uma informaçãozinha de log, depois podia receber como parâmetro um callback, etc.

Tudo coisa que a medida que a gente precisar, a gente vai fazer. 

Aqui é o tópico. 

A chave e o valor já são parâmetros, vou usar aqui, imprimir, etc., o get é uma exception que pode ocorrer, então eu estou jogando aqui na exception.

e agora o e-mail, como é que eu faço o dispatcher do e-mail?

Então só dispatcher.send, o tópico que é esse aqui, a chave e o valor. 

A chave eu estou usando a mesma, que é o ID do usuário e o valor é o e-mail que eu vou enviar. 

Então, estão aí os dois sends. 

Vamos testar esse cara?

Então, o e-mail e o fraud já estão rodando. 

Então, quando eu testar, ele deve mandar 10 mensagens, vamos ver. 

Tentando, enviou as 10 mensagens, 20 no total, 10 de cada tópico e aqui, você vê ele recebendo essas mensagens, está funcionando.

Eu quero só refinar uma coisinha, quando a gente tem recursos como esses, que são Kafka producer, que fica com porta aberta, é comum a gente querer dar um close. 

Então a interface que ajuda a gente a implementar o close se chama closeable.

E aí, você coloca a função close, que vai fazer Um producer.close e aí, você armazenou agora um estado, podia estar aberto, podia estar fechado, podia estar o que fosse, mas agora a gente tem um close aqui explicito,

O legal de fazer o close desse maneira é que a gente pode usar as características do Java para tentar executar esse código, se acontecer qualquer exception aqui dentro, qualquer exception que aconteça aqui dentro, o que que ele vai tentar fazer? 

Ele vai fechar esse cara aqui para a gente.

Então, qualquer coisa que aconteça aqui, ele vai fechar o Kafka dispatcher, ele está falando: “Pode acontecer um IO exception”, sim, mas você poderia tirar aqui o throws IO exception, porque não acontece. 

Então, nesse caso específico, não tem IO exception, nessa tentativa.

Então agora, não importa, se der sucesso ou se sair por causa de um exception, o seu recurso será fechado, vai ser fechado esse dispatcher aqui. 

Poderia fazer a mesma coisa no nosso service, a gente tem aqui o nosso service, a gente poderia ter um try (catch).

Então, a gente poderia ter um try, quando a gente cria o service e fecha depois do run, então eu faria um try aqui, try nesse cara e um fechar nesse cara aqui, como é que a gente faz isso? 

O Kafka service tem que implementar closeable e a função do closeable é a close, que no nosso caso, não joga exception.

E aí, ele simplesmente fala: “consumer.close”, fechou, ele fecha o nosso consumidor. 

Então aqui, a gente também tem essa garantia de que independente de sair com sucesso ou com exception, ele vai fechar o que tiver que fechar, isso no fraud detector service.

Claro, no e-mail, a gente tem que fazer a mesma coisa, um try e fechar aqui assim, mas é super rápido, direto. 

Então, repara que a gente tem uma refatorada e já começou a colocar uma camada, escondendo um pouco do Kafka para a gente.

Repara que cada vez menos a gente importa coisa do Kafka, a gente só está importando aqui o consumer record, porque a gente recebe o consumer record, a gente consegue transformar isso também. 

Eu acho que para a nossa camada, receber o consumer record faz todo o sentido.

E no dispatcher, na hora que a gente despacha aqui, vamos tirar os imports, a gente não está importando nada do Kafka, mais direto ainda, da maneira que a gente está trabalhando por enquanto. 

Então, a gente vai extraindo, refatorando e deixando mais limpo o nosso código.


KafkaDispatcher.java
```
public class KafkaDispatcher implements Closeable {

    private final KafkaProducer<String, String> producer;

    KafkaDispatcher() {
        this.producer = new KafkaProducer<String, String>(properties());
    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092"); // ip e porta do kafka
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName()); // nome da classe de deserializaçao da chave
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName()); // nome da classe de deserializaçao da mensagem
        return properties;
    }

    public void send(String topic, String key, String value) throws ExecutionException, InterruptedException {
        var record = new ProducerRecord<String, String>(topic, key, value); // parametros: topico, chave, mensagem

        Callback callback = (data, ex) -> {
            if (ex != null) {
                ex.printStackTrace();
                return;
            }
            System.out.println("sucesso enviando nesse topico: " + data.topic() + "::: partition " + data.partition() + "/ offset" + data.offset() + "/ timestamp" + data.timestamp());
        };

        producer.send(record, callback).get(); // envia a mensagem sincrona com callback (lambda)
    }

    @Override
    public void close() {
        producer.close();
    }
}
```

NewOrderMain.java
```
public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try(var dispatcher = new KafkaDispatcher()) {

            for (int i = 0; i < 10; i++) {
                var key = UUID.randomUUID().toString();
                var value = key + "- 12345, 6789, 1209";
                dispatcher.send("ECOMMERCE_NEW_ORDER", key, value);

                var email = "thank you for your order! we are processing your order!";
                dispatcher.send("ECOMMERCE_SEND_EMAIL", key, value);
            }
        }
    }
}
```

FraudDetectorService.java
```
public class FraudDetectorService {
    public static void main(String[] args) {
        var fraudService = new FraudDetectorService();
        try(var service = new KafkaService(
                FraudDetectorService.class.getSimpleName(), // group
                "ECOMMERCE_NEW_ORDER", // topic
                fraudService::parse // parse function
        )) {
            service.run();
        }
    }

    private void parse(ConsumerRecord<String, String> record) {
        System.out.println("-----------------");
        System.out.println("Processando new order, checking for fraud");
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println(record.partition());
        System.out.println(record.offset());

        try {
            // simular um serviço demorado
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // ignoring
            throw new RuntimeException(e);
        }
        System.out.println("Order processed");
    }
}
```

EmailService.java
```
public class EmailService {
    public static void main(String[] args) {
        var emailService = new EmailService();
        try (var service = new KafkaService(
                EmailService.class.getSimpleName(),
                "ECOMMERCE_SEND_EMAIL",
                emailService::parse
        )) {
            service.run();
        }
    }

    private void parse(ConsumerRecord<String, String> record) {
        System.out.println("-----------------");
        System.out.println("sending email");
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println(record.partition());
        System.out.println(record.offset());

        try {
            // simular um serviço demorado
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // ignoring
            throw new RuntimeException(e);
        }
        System.out.println("email sent");
    }
}
```

KafkaService.java
```
class KafkaService implements Closeable {
    private final KafkaConsumer<String, String> consumer;
    private final ConsumerFunction parse;

    KafkaService(String groupID, String topic, ConsumerFunction parse) {
        this.parse = parse;
        this.consumer = new KafkaConsumer<String, String>(properties(groupID));
        consumer.subscribe(Collections.singletonList(topic)); // inscriçao nos topicos ouvidos
    }

    void run() {
        while (true) { // fica chamando o kafka para procurar mensagens
            var records = consumer.poll(Duration.ofMillis(100)); // consulta o kafka por mais mensagens

            if (!records.isEmpty()) {
                System.out.println("encontrei " + records.count() + " registros");
                for (var record : records) {
                    parse.consume(record);
                }
            }
        }
    }

    private static Properties properties(String groupID) {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());// deserializador da chave
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()); // deserializador de mensagens
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupID);// consumer group name
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());// client name

        return properties;
    }

    @Override
    public void close() {
        consumer.close();
    }
}
```

### 