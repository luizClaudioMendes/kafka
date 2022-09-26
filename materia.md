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
    - [O que aprendemos?](#o-que-aprendemos-2)
  - [Serializaçao customizada](#serializaçao-customizada)
    - [Diretórios do Kafka e Zookeeper](#diretórios-do-kafka-e-zookeeper)
    - [Serialização com GSON](#serialização-com-gson)
    - [Migrando o log](#migrando-o-log)
    - [Deserialização customizada](#deserialização-customizada)
    - [Lidando com customizações](#lidando-com-customizações)
      - [Propriedades extras de configuraçao do Kafka service, dependendo de quem a cria](#propriedades-extras-de-configuraçao-do-kafka-service-dependendo-de-quem-a-cria)
    - [O que aprendemos?](#o-que-aprendemos-3)
  - [Microserviços e módulos](#microserviços-e-módulos)
    - [Microsserviços como módulos em um mono repo](#microsserviços-como-módulos-em-um-mono-repo)
      - [Criando um novo modulo no maven](#criando-um-novo-modulo-no-maven)
    - [Binários dos microsserviços](#binários-dos-microsserviços)
    - [O que aprendemos?](#o-que-aprendemos-4)
- [kafka: fast delegate, evoluçao e cluster de brokers](#kafka-fast-delegate-evoluçao-e-cluster-de-brokers)
  - [novos produtores e consumidores](#novos-produtores-e-consumidores)
    - [Produtores consumidores e o eager de patterns](#produtores-consumidores-e-o-eager-de-patterns)
    - [Um serviço que acessa bancos externos](#um-serviço-que-acessa-bancos-externos)
      - [SQLite](#sqlite)
    - [O que aprendemos?](#o-que-aprendemos-5)
  - [evoluindo um serviço](#evoluindo-um-serviço)
    - [Evoluindo serviços e schemas](#evoluindo-serviços-e-schemas)
    - [Escolhendo o id adequado](#escolhendo-o-id-adequado)
    - [O que aprendemos?](#o-que-aprendemos-6)
  - [servidor HTTP](#servidor-http)
    - [Usando um servidor http como ponto de entrada](#usando-um-servidor-http-como-ponto-de-entrada)
      - [Jetty como servidor http](#jetty-como-servidor-http)
    - [Fast delegate](#fast-delegate)
    - [O que aprendemos?](#o-que-aprendemos-7)
  - [cluster de brokers](#cluster-de-brokers)
    - [Single point of failure do broker](#single-point-of-failure-do-broker)
    - [Replicação em cluster](#replicação-em-cluster)
    - [Cluster de 5 brokers e explorando líderes e réplicas](#cluster-de-5-brokers-e-explorando-líderes-e-réplicas)
      - [consumer offset replication](#consumer-offset-replication)
    - [Acks e reliability](#acks-e-reliability)
      - [configurar os ACKS](#configurar-os-acks)
        - [ACKS = 0](#acks--0)
        - [ACKS = 1](#acks--1)
        - [ACKS = all](#acks--all)
  - [O que aprendemos?](#o-que-aprendemos-8)
- [kafka: batches, correlation ids e dead letters](#kafka-batches-correlation-ids-e-dead-letters)
  - [Batch](#batch)
    - [Simulando a geração de relatórios](#simulando-a-geração-de-relatórios)
    - [Generalização de processo de batch assíncrono e http fast delegate](#generalização-de-processo-de-batch-assíncrono-e-http-fast-delegate)
      - [fast delegate](#fast-delegate-1)
    - [Batch assíncrono em execução](#batch-assíncrono-em-execução)
    - [O que aprendemos?](#o-que-aprendemos-9)
  - [Serializaçao e deserialização customizada](#serializaçao-e-deserialização-customizada)
    - [A importância de um CorrelationId](#a-importância-de-um-correlationid)
    - [A serialização customizada com correlation id e um wrapper](#a-serialização-customizada-com-correlation-id-e-um-wrapper)
    - [Deserialização customizada](#deserialização-customizada-1)
    - [O que aprendemos?](#o-que-aprendemos-10)
  - [Correlation ID](#correlation-id)
    - [Implementando o correlation id](#implementando-o-correlation-id)
    - [O que aprendemos?](#o-que-aprendemos-11)
  - [Arquitetura e falhas atá agora](#arquitetura-e-falhas-atá-agora)
    - [Revisando a arquitetura até agora](#revisando-a-arquitetura-até-agora)
    - [Revisando o rebalanceamento](#revisando-o-rebalanceamento)
    - [O que aprendemos?](#o-que-aprendemos-12)
  - [Assincronicidade, retries e deadleatters](#assincronicidade-retries-e-deadleatters)
    - [Retries e assincronicidade](#retries-e-assincronicidade)
      - [in flight requests per session](#in-flight-requests-per-session)
      - [garantias do Kafka](#garantias-do-kafka)
    - [Enviando mensagem de deadletter](#enviando-mensagem-de-deadletter)
    - [O que aprendemos?](#o-que-aprendemos-13)
- [Kafka: idempotencia e garantias](#kafka-idempotencia-e-garantias)
  - [Organização e lidando com múltiplos tópicos de envio em um mesmo serviço](#organização-e-lidando-com-múltiplos-tópicos-de-envio-em-um-mesmo-serviço)
    - [Micro serviços de email e fast delegate real](#micro-serviços-de-email-e-fast-delegate-real)
  - [camada de serviços](#camada-de-serviços)
    - [Extraindo uma camada de serviços](#extraindo-uma-camada-de-serviços)
    - [Paralelizando com pools de threads](#paralelizando-com-pools-de-threads)
    - [Facilidade de criar novos serviços](#facilidade-de-criar-novos-serviços)
    - [O que aprendemos?](#o-que-aprendemos-14)
  - [commits e offsets](#commits-e-offsets)
    - [Offset latest e earliest](#offset-latest-e-earliest)
      - [OFFSET_RESET_CONFIG](#offset_reset_config)
      - [AUTO_OFFSET](#auto_offset)
      - [AUTO_OFFSET_RESET_CONFIG](#auto_offset_reset_config)
    - [O que aprendemos?](#o-que-aprendemos-15)
  - [Lidando com mensagens duplicadas](#lidando-com-mensagens-duplicadas)
    - [O problema da mensagem duplicada](#o-problema-da-mensagem-duplicada)
      - [AUTO_COMMIT_INTERVAL_MS_CONFIG](#auto_commit_interval_ms_config)
      - [ACKS_CONFIG](#acks_config)
  - [Kafka transacional](#kafka-transacional)
      - [TRANSACTIONAL_ID_CONFIG](#transactional_id_config)
      - [MAX_IN_FLIGHT_REQUESTS](#max_in_flight_requests)
      - [ENABLE_AUTO_COMMIT](#enable_auto_commit)
      - [AUTO_OFFSET_RESET_CONFIG](#auto_offset_reset_config-1)
      - [ISOLATION_LEVEL](#isolation_level)
      - [Kafka Transactions (https://itnext.io/kafka-transaction-56f022af1b0c)](#kafka-transactions-httpsitnextiokafka-transaction-56f022af1b0c)


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

### O que aprendemos?
* A importância de evitar copy e paste
* Criando nossa camada de abstração
* Criando nosso Dispatcher
* Criando nosso Service

## Serializaçao customizada
### Diretórios do Kafka e Zookeeper
Numa das atividades, eu citei que de tempo em tempo, se você startar a sua máquina, a configuração padrão do server properties e do zookeeper, é que os arquivos serão guardados no diretório /tmp no Mac e no Linux, isso é, num diretório temporário, no Windows também é um diretório temporário.

O que que isso vai acontecer? 

Em qualquer sistema operacional, se você está armazenando dados em um diretório temporário, quando você menos espera, esses arquivos podem desaparecer. 

Então, de tempo em tempo, se os seus arquivos desaparecerem, você vai ter que começar de novo.

Então tem duas maneias de lidar com isso, uma é, quando você quiser manter um diretório para valer, onde vão ficar as suas mensagens, o que você vai fazer? 

Você vai pegar esse diretório, por exemplo, eu estou no diretório aqui onde está o meu Kafka, lembra que é o diretório apps.

Então, eu vou criar um diretório aqui dentro chamado data, que é os dados. 

Dentro do data, eu vou criar a pasta do zookeeper e eu vou criar também o do kafka, então tem esses dois diretórios. 

Então repara que dentro do data, eu vou ter esses dois diretórios.

Então, /zookeeper, /Kafka, são os diretórios que eu quero utilizar. 

Vou voltar aqui e vou editar os arquivos de configuração, o config/server.properties, em algum lugar, ele fala diretório, olha aqui, tmp kafka logs, então invés de ter tmp/kafka-logs, onde que eu vou guardar? 

no diretório que você queria. 

No meu caso é data/kafka.

Configurar o zookeeper.properties, também tem um lugar que fala de diretório, tmp/zookeeper, vai virar tudo isso /zookeeper. 

Então, se eu der um ls aqui no diretório data, eu vou ver que eu tenho tanto o zookeeper, quanto o Kafka.

Então, eu vou dar stop primeiro no Kafka. 

Se eu quisesse, eu podia olhar o diretório tmp, que a gente vai ter lá tanto o zookeeper, quanto o kafka logs, tem tudo lá.

Então, eu poderia simplesmente remover a qualquer instante o kafka logs e o tpm zookeeper, a qualquer instante que eu quisesse. 

Então, vamos rodar o zookeeper agora, com o diretório novo e o kafka com o diretório novo.

eu não devo ter agora nenhum tópico, se eu der uma olhada, eu pedi: “Me fala os tópicos”, não deve ter tópico, porque é tudo novo, vamos dar uma olhada? 

É tudo novo. 

Então, se eu der uma olhadinha aqui no diretório data zookeeper, já tem o zookeeper lá dentro, os arquivos de dados dele.

E do Kafka também já tem os dados dos tópicos, só cuidado, o Kafka chama de log, mas não é um log que simplesmente: “Ah, é uma linha opcional, pode jogar fora, pode apagar”, não, log é no sentido de registro, são os registros que o Kafka armazenou e o Kafka precisa desses registros armazenados por determinado tempo ou tamanho em disco, configurações do arquivo.

Então, eu estou agora com o Kafka, com o diretório fixo, trabalhando mais bonitinho, o que que eu quero fazer? 

O que eu gostaria de fazer era parar de usar string para tudo que é lado. 

### Serialização com GSON
Por enquanto, a gente está utilizando string em tudo que é lado, eu gostaria, que eu tivesse um pedido de compra, uma ordem que está sendo enviada de um lado para o outro, eu gostaria de uma order, de verdade.

Então, quer dizer que quando eu estou enviando, NewOrderMain, eu gostaria de despachar uma order de verdade, então o Kafka dispatcher não vai despachar mais um valor que é uma string, mas sim um order.

Mas calma aí, não vai despachar sempre um order, porque, por exemplo, por e-mail vai ser uma string, então quer dizer, vai depender de acordo com o dispatcher que você precisa. 

Então, eu queria trabalhar agora com serialização, mas para trabalhar com serialização, eu precisava que o meu Kafka dispatcher fosse customizado de acordo com o tipo.

Então cada linguagem vai ter uma maneira de trabalhar com isso, no Java isso é chamado generics, então o Kafka dispatcher, ele vai ser do tipo “T”, “T” de tipo, é a letra que costuma ser usada. 

Então no new order main, quando a gente cria um Kafka dispatcher, eu vou falar que o meu Kafka dispatcher é de uma order.

Eu vou ter que criar a order, o que que a classe order vai ter? 

Vamos var uma olhadinha aqui, tem que ter três valores, o ID do usuário, o ID da compra e o valor, por exemplo, são esses três dados.

Então vou colocar aqui os três dados no private final string userId, orderId, por fim, além do usuário e da compra, do ID da compra, eu preciso do valor, do valor total, do amount, Vamos usar o BigDecimal, que no Java tem uma representação com ponto flutuante e a gente consegue ter mais precisão nas casas decimais, gerar um construtor que recebe tudo, já que tudo é obrigatório. 

Então essa é a minha classe order, recebe... eu tenho que criar uma order aqui.

Então, invés de criar um value aqui, eu vou criar aqui a minha order, que é uma nova order e a order recebe o quê mesmo? 

O userId, o orderId e o amount. 

O userId é esse aqui e o orderId? 

Vamos criar um outro ID, o orderId é o outro ID e o amount?

O amount seria um número aleatório, um Math.random, pode ser um random, então um número aleatório, só que o random é um número entre 0 e 1, se ele é entre 0 e 1, vamos fazer ele entre 0 e 5.000, mas eu quero que ele seja pelo menos um real, então pelo menos um real.

Então esse daqui é o meu valor, o meu valor está aqui, só um cuidado, porque o amount, ele é o que agora? 

Ele é um double, porque o math.random devolve um double. 

Então, eu quero transformar o double num BigDecimal.

Então, no BigDecimal, quando a gente instancia ele, eu passo ele... o amount como parâmetro, então eu vou colocar tudo isso aqui dentro, new BigDecimal, então eu criei aqui um valor aleatório como amount, eu posso tirar isso daqui e eu tenho tudo o que eu queria.

Claro, o userId vem aqui, que são strings, estou feliz, não tenho preocupação, mas aqui a minha order, eu vou enviar a minha order e aí, não funciona, por quê? 

Porque o método send requer um “T”.

Se “T” for string, envia string, se “T” é order, envia order e por aí vai. 

Então esse é o generics do Java, é isso que a gente está usando pelo menos para enviar, para receber vai precisar de mais um detalhe, em qualquer linguagem você vai ter um análogo a isso.

Então, essa é a parte de programação de linguagem e de modelo, como que a gente modela, eu vou enviar mensagens que tenham as informações que eu preciso, bem estruturadas e agora a gente está fazendo isso.

o producer é de string, string mesmo? 

Tanto que está dando erro aqui, o send, o record, não está dando certo, por quê?

Porque ele está esperando um producer record de string, string, mas ele recebeu um producer record de string e “T”, por que, então? 

Porque o producer também tem que ser um produtor de chave string, valor “T”. Então agora, ele consegue enviar aqui, send, ele consegue enviar.

“Guilherme, o dispatcher do e-mail não está funcionando mais”, não está, por quê?

Porque o e-mail, como que a gente está fazendo por enquanto?

O e-mail recebe string ou recebe pedido? 

O e-mail é string, então enquanto essa mensagem, a gente espera que receba uma order, essa mensagem, a gente espera que receba uma string, então não pode ser mais o mesmo dispatcher, tem que ser dois dispatcher diferentes, um para order e um para string, eu teria que ter dois dispatchers distintos.

E não tem o que fazer, agora eu vou precisar de dois dispatcher mesmo, não vou ter como fugir disso, tem maneiras, vou da maneira mais simples mesmo, que é um dispatcher... um é o dispatcher de order, que é um order dispatcher e o outro é o e-mail dispatcher, para mim, eu vou deixar isso bem claro.

Um é o e-mail dispatcher e o outro é o order dispatcher, formato tudo bonitinho, então eu tenho o order e o e-mail dispatcher, esse é o order dispatcher, esse é o e-mail dispatcher, só o cuidado que o e-mail dispatcher despacha e-mail. 

Posso tentar rodar, só o new order main, mesmo que não tenha ninguém escutando, só quero verificar que o envio está funcionando, se eu estou falando desse jeito, é porque a gente pode esperar que ele não vá funcionar.

Então, bom, alguma coisa deu de errado. 

Primeiro, ele falou: “StringSerializer”, deu erro na serialização, ele falou: “A classe order não pode ser convertida para string”, mas você fala: “Mas, Guilherme, eu estou mandando um order, eu não estou mandando string”.

Sim, mas o Kafka não sabe transformar order em string, a gente tem que ensinar, porque no final o Kafka quer transformar o seu objeto, a sua mensagem em bytes e ele sabe fazer isso através do string serializer, olha aqui, a gente falou para ele usar o string serializer, então ele sabe transformar string em bytes, mas ele não sabe transformar order em bytes.

Então, a gente precisa falar qual o serializador que a gente quer usar, a gente não quer mais usar o string serializer, a gente que usar um serializador que serializa para algum formato, é muito comum que se utilize um formato humanamente, razoavelmente legível, então é comum usar Json, você poderia usar outros formatos, claro.

Então, existe uma biblioteca que transforma coisas em Json, que é a Gson, então a gente pode ir no nosso pom, aqui no pom, a gente pode adicionar uma nova dependência.

Qual é a dependência que eu quero adicionar? 

Maven repository Gson, “G” de Google, Gson. O Gson, na versão 2.8.6.

Então ele já vai baixar, quando terminar de baixar, esse vermelho vai virar branquinho para dizer: “Baixei”. 

Eu vou ter que falar para ele que olha, para serializar o valor, eu vou usar o meu Gson serializer.

Aí, você fala: “Guilherme, não existe”, não existe mesmo, a gente tem que criar, o Gson existe, ele serializa, mas ele serializa através de uma classe chamada “GsonBuilder.create”, isso daqui devolve para mim, para a gente um Gson, que é um serializador, esse daqui é um serializador.

Esse serializador aqui, ele não funciona para o Kafka, no Kafka, a gente tem que implementar a interface serializer do Kafka, serializer do que? 

De qualquer coisa, o serializer que a gente implementa, tem que ser serializer de qualquer coisa.

Então, vou importar o serializer do Kafka e agora sim, eu estou trabalhando com uma questão do Kafka código, não mais Kafka arquitetura, questão de mensagem, não mais Java, questão de generics, que todas as linguagens vão ter de sua maneira.

Então agora, eu tenho de serializar, eu tenho que serializar de uma string, para um “T”. 

Para me virar com isso daí, vamos aqui no serializer, a gente tem o byte serializer, é isso que vou ter que implementar.

Então, para serializar, o que que eu falo? 

Eu falo: “Gson, serializa aí para mim, para Json, toJson, esse objeto aqui”, esse daqui é o “T”, então: “Serializa para mim esse objeto”, então ele serializa, só que isso daqui devolve uma string e aí, eu transformo em bytes, eu transformo a string em bytes.

Então esse é o serializer. 

Tem outros métodos que a gente pode... isso dá override, implementar? 

Tem, tem um de configuração, tem um de fechamento, então tem vários que a gente pode implementar, tem vários que a gente pode implementar aqui, por que que a gente não implementa?

Porque ele já tem um padrão que não faz nada, a gente não ia fazer nada mesmo, então tudo bem, eu não tenho problema com isso. 

Então, a serialização padrão é simplesmente transformar em string e acabou, eu estou feliz com isso, não estou preocupado com isso daí.

Então, o que que eu quero fazer agora? 

Então isso daqui vai usar esse serializador, vamos ver se agora vai funcionar? 

Se ele consegue mandar as duas mensagens? 

Vou rodar de novo e vamos ver se ele vai serializar esse daqui e serializar esse outro.

Alguma coisa ele fez, ele está falando: “Sucesso, enviando”, enviou várias mensagens, seria legal a gente ver a mensagem. 

Então o nosso send faz isso, como é que eu posso ver as mensagens que estão sendo enviadas?

Já serializadas. 

Para elas chegarem serializadas no nosso (lado), a gente precisava de alguma maneira ver tudo o que está sendo enviado, independente do que seja, a gente quer ver o que está sendo enviado. 

Isso é meio que um tipo de um log, eu queria ver um log.

Então, se eu quero ver um log, a gente tem que fazer o log desse serializer, a gente tem que fazer o log service. 

Lembra do log service? 

Só que o log service ainda está da maneira antiga. 

Então, agora que a gente é capaz de enviar serializado com o Json, apesar de a gente não ver ela, eu queria ser capaz agora de deserializar.

Primeiro como string mesmo, só para ver que funcionou e depois a gente desserializa para um pedido, é isso que a gente vai fazer daqui a pouquinho.

NewOrderMain.java
```
public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try(var orderDispatcher = new KafkaDispatcher<Order>()) {
            try(var emailDispatcher = new KafkaDispatcher<String>()) {
                for (int i = 0; i < 10; i++) {
                    var userId = UUID.randomUUID().toString();
                    var orderId = UUID.randomUUID().toString();
                    var amount = new BigDecimal(Math.random() * 5000 + 1);

                    var order = new Order(userId, orderId, amount);
                    orderDispatcher.send("ECOMMERCE_NEW_ORDER", userId, order);

                    var email = "thank you for your order! we are processing your order!";
                    emailDispatcher.send("ECOMMERCE_SEND_EMAIL", userId, email);
                }
            }
        }
    }
}
```

Order.java
```
public class Order {

    private final String userId, orderId;
    private final BigDecimal amount;

    public Order(String userId, String orderId, BigDecimal amount) {
        this.userId = userId;
        this.orderId = orderId;
        this.amount = amount;
    }

    public String getUserId() {
        return userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
```

KafkaDispatcher.java
```
public class KafkaDispatcher<T> implements Closeable {

    private final KafkaProducer<String, T> producer;

    KafkaDispatcher() {
        this.producer = new KafkaProducer<>(properties());
    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092"); // ip e porta do kafka
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName()); // nome da classe de deserializaçao da chave
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GsonSerializer.class.getName()); // nome da classe de deserializaçao da mensagem
        return properties;
    }

    public void send(String topic, String key, T value) throws ExecutionException, InterruptedException {
        var record = new ProducerRecord<>(topic, key, value); // parametros: topico, chave, mensagem

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

GsonSerializer.java
```
public class GsonSerializer<T> implements Serializer<T> {

    private final Gson gson = new GsonBuilder().create();

    @Override
    public byte[] serialize(String s, T object) {
        return gson.toJson(object).getBytes();
    }
}
```

pom.xml
```
....
 <!-- https://mvnrepository.com/artifact/com.google.code.gson/gson -->
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.9.1</version>
</dependency>
....
```

### Migrando o log
O nosso próximo passo é migrar o código do log service para aquela versão nova, aquela versão que invés de usar Kafka consumer, usa o nosso Kafka service.

Quero criar um Kafka service direto, então ele vai funcionar que nem os outros serviços, quem nem, por exemplo, o e-mail service.

Aí, a gente cria um Kafka service, através de um try e depois da um run.

Criei o Kafka service, eu passo o meu log service.parse, eu falo quais são os tópicos que eu quero escutar, e-commerce qualquer coisa, eu falo o nome do meu log service, o nome do meu grupo, consumer group, o consumer group é o log service.

E aí, é só eu consumir. 

Dessa maneira, então eu posso apagar todo esse resto e implementar o parse aqui, vai receber um record, para cada record, aqui, eu já estou aqui dentro.

Já tenho o topic aqui, value e tudo mais sendo impresso. 

Então, isso daqui é o que deveria funcionar, só tem um detalhe, o log usa uma regex.

Então, o que que a gente tem que fazer? 

A gente tem que mudar um pouquinho, ao invés de receber uma string topic, eu tenho que falar: “Esse daqui específico, ele é um pattern regex.

Então, a gente vai ter que criar um outro construtor, a gente precisa de dois construtores, a gente precisa desse primeiro que recebe um topic como string e a gente precisa desse segundo que recebe um topic com um pattern, como pattern, o que que ele tem que fazer mesmo?

Ele tem que, invés de setar o subscribe essa forma, ele seta outro subscribe, ele fala: “Subscribe esse tópico, esse pattern”, então a única diferença é essa, “Ah, essas duas linha são iguais, posso jogar num construtor e num construtor?”, posso, poderia jogar aqui no construtor, mas não é um Extract Method.

aqui eu vou querer o groupId e o parse, então eu vou falar: this, parse, groupId, ele vai criar para a gente esse construtor, esse construtor é privado e chamar simplesmente de this parse groupId.

Então, se você quisesse evitar aquela “linha copy, paste”, eu posso fazer isso, então eu tenho dois construtores que eu posso utilizar e tenho um privado que não dá subscribe em nada, que não faz nada, só inicializa os dois campos. 

Então, eu estou sempre (inicializando), num eu estou num tópico fixo, no outro eu estou num pattern.

Então aqui eu tenho esse cara, posso querer escutar, vou rodar ele. 

Está escutando, olha, new order e send e-mail, está escutando os dois. 

Lembra, você fala: “Ah, Guilherme, mas ele já deveria ter offset aí, né?”

Sim, ele já pulou o offset, por quê? 

Porque o group consumer, o consumer group não existia quando a gente enviou as mensagens, o consumer group log service não existia ainda, então ele começou do último. 

Vou tentar rodar aqui, vamos gerar 10 mensagens, 20 no total, 10 de e-mail e 10 de orders e vemos no log agora e aqui eu tenho o log.

Encontrei um registro, olha o registro dele aqui, olha isso aqui, é um Json, é um Json serializado, bonitinho para a gente com o BigDecimal, com 200 casas decimais, não é o nosso foco, não tem problema.

O e-mail bonitinho, olha, quando é só uma string, o Json é só uma string mesmo, então não tem problema, poderia ser só uma string e aqui a gente tem o Json de novo de um objeto, aqui é uma string, aqui um objeto, aqui uma string e por aí, vai.

Então, realmente a serialização funcionou e a gente migrar o log service também funcionou, por quê? 

Porque a gente está recebendo como string. 

No final, a msg é uma string, a gente está usando ela como uma string, porque a gente está pegando todo o nosso objeto, transformando uma string, que é um Json e transformando isso em bytes.

String em bytes, o string deserializer consegue fazer, então o log service, mesmo ele utilizando o string deserializer, mesmo ele usando o string deserializer, ele consegue recuperar esse string. A questão é: no log, a gente quer um string deserializer, só o log mesmo.

Mas no fraud service, aí, a coisa muda, porque no fraud detector service, eu não quero trabalhar como uma string, eu quero trabalhar com a order, então eu não quero desserializar para string, eu quero desserializar para order.

Então o nosso próximo passo, agora que a gente já é capaz de ver a mensagem intermediária, é desserializar ela.

LogService.java
```
public class LogService {
    public static void main(String[] args) {
        var logService = new LogService();
        try (var service = new KafkaService(
                LogService.class.getSimpleName(),
                Pattern.compile("ECOMMERCE.*"),
                logService::parse)) {
            service.run();
        }
    }

    private void parse(ConsumerRecord<String, String> record) {
            System.out.println("-----------------");
            System.out.println("LOG");
            System.out.println(record.topic());
            System.out.println(record.key());
            System.out.println(record.value());
            System.out.println(record.partition());
            System.out.println(record.offset());
    }
}
```

KafkaService.java
```
class KafkaService implements Closeable {
    private final KafkaConsumer<String, String> consumer;
    private final ConsumerFunction parse;

    KafkaService(String groupID, String topic, ConsumerFunction parse) {
        this(parse, groupID);
        consumer.subscribe(Collections.singletonList(topic)); // inscriçao nos topicos ouvidos
    }

    KafkaService(String groupID, Pattern topic, ConsumerFunction parse) {
        this(parse, groupID);
        consumer.subscribe(topic); // inscriçao nos topicos por regex
    }

    private KafkaService(ConsumerFunction parse, String groupID) {
        this.parse = parse;
        this.consumer = new KafkaConsumer<String, String>(properties(groupID));
    }
...
```

### Deserialização customizada
Chegou a hora de a gente fazer a desserialização e a desserialização vai ter o mesmo problema em qualquer linguagem, em qualquer ferramenta que a gente esteja utilizando, que é a seguinte, eu tenho o fraud detector service e estou criando um Kafka service.

Quando eu **crio o meu Kafka service**, **eu vou falar de que tipo que ele é**, **que tipo que eu pretendo desserializar**, então é a mesma coisa que o producer, a gente tem que falar: “**Eu quero desserializar uma order, eu não quero desserializar qualquer coisa**”.

Então quer dizer que o Kafka service, tem que ter um “T” para ser capaz de desserializar isso. 

Isso quer dizer que quando a gente roda e pega o record, o record, que a gente está dando um poll desse Kafka consumer, ele não é mais de string para string, antes ele era de string para string, chave string, valor string.

Não, agora ele é chave string e **tipo “T”**, repara, o Kafka service passa a reclamar aqui em baixo, por quê? 

Porque o parse consume é <string, string>, então a função de consumo agora é de chave string e tipo “T”, a gente tem que ir fazendo com que tudo seja capaz de trabalhar com qualquer tipo.

Então, em qualquer linguagem a gente vai ter o problema do (gênero) e alguma solução. 

Na hora que eu tentar desserializar, qual desserializador que ele vai utilizar? 

Ele vai utilizar o string deserializer...

Eu quero usar o Gson desserializar, que a gente vai ter que criar da mesma maneira que a gente criou o serializer. 

Então, vamos criar o nosso deserializer, como é que funciona o deserializer? 

Mesma coisa, vamos precisar de uma instância do Gson.

Implementar deserializer do tipo “T”, do Kafka. 

No deserializer, o que que a gente quer fazer? 

A gente quer falar: “Recebi os bytes”, então eu vou falar: “Gson, desserializa para mim aí, baseado no Gson aqui, .fromJson e aí, eu vou falar o Json, os bytes, mas não funcionam, por quê?

Porque quando você desserializa, você precisa saber qual é o tipo que você vai desserializar, se está desserializando uma string, você está desserializando um order? 

Desserializando o quê? 

Existem bibliotecas (extreme), etc., que tenta inferir isso dos dados da mensagem.

Existem vantagens e existem desvantagens, o Gson não tenta inferir isso, então o que que a gente tem que fazer? 

Passar par ele o tipo, uma class. 

Só que “T” não é um class, “T” é uma coisa em tempo de compilação, não é em tempo de execução, que a gente passa como parâmetro.

Eu vou ter que passar aqui a classe, em geral, a classe é **class**, que **é uma palavra chave**, se usa **clazz** ou você usa **type**, porque type é uma maneira mais genérica de dizer: qualquer tipo, poderia ser um menu, uma classe, uma interface, seja lá o que for.

Então eu vou usar um type, qual que é o type?

Não sei, como é que eu descubro qual que é o type? 

Vamos fazer assim, quando você cria Gson deserializer, você passa o tipo para ele, mas calma aí, a gente não criou o Gson serializer, a gente só passou o nome da classe.

Então, para isso, existe a função, o método **configure**, que a gente pode dar override e o que que a gente pode fazer aqui? 

Aqui a gente recebe as configurações do nosso Kafka, essas configurações aqui.

Então o que a gente pode fazer é... falar assim: 

Nas configuração do meu serviço, vai ter uma propriedade: GsonDeserializer e aí, eu crio aqui uma constante, para a gente ter uma variável de configuração.

Eu posso falar que essa daqui seja o type config, é o tipo que a gente vai utilizar. 

Então o meu type config, vai ser o quê? 

Vai ser por padrão string.

Então o meu type config, é só eu criar uma instância num valor qualquer, eu vou colocar "br.com.alura.ecommerce.type_config", uma string qualquer e aí, o que eu vou fazer é, aqui eu passei, o string.class. 

Aí, você fala: “Ah, tem que ser string, porque é properties”, então “.getName”.

Essa daqui é a classe que eu quero, aí o que que eu faço agora? 

No meu deserializer aqui, eu tenho esse meu valor, no meu deserializer, eu tenho o momento do configuração. 

Então, eu posso falar: “Configurações, me dá para mim esse type config”, ele vai devolver para a gente qual?

O nome do tipo, o type name. 

Aí, eu preciso pregar esse type name, que na verdade, dentro do config, repara o que que ele é um ponto de interrogação, então a gente precisa transformar isso numa string.

A gente pode chamar o to.string, só que ele tem o problema do nulo, e para evitar o problema do nulo, eu vou chamar um **valueOf**. 

Então, o que é que o (object) valueOf faz? 

Se for nulo, ele devolve nulo, senão ele devolve o obj.string.

Então eu tenho o type, eu vou transformar isso numa classe, **class.forName**, typeName e o forName é o meu type, só um cuidado, tem que colocar try/catch, porque a classe pode não existir, você pode ter passado uma classe errada.

Se você passou uma classe errada, eu quero que de erro, eu não quero que dê certo, nesse caso, eu vou jogar um exception, para que dê um erro mesmo. 

Então é type for deserialization does not exist in the classpath, ele não existe no classpath.

Então é só eu armazenar isso daqui como uma variável membro, como um membro, variável membro aqui, class type e aí, utilizar esse type aqui.

Se eu utilizar o type aqui, ele vai devolver para mim o que eu quero, só tenham cuidado, ele tem que ser um type do tipo “T”, então o que que eu tenho que fazer? 

Eu coloco aqui do tipo “T”, quando eu coloco do tipo “T” aqui, eu tenho que forçar goela abaixo, fingindo que essa classe, eu sei que ela é do tipo “T”.

Então aqui, eu sei que ela é do tipo “T”, o que faltou é, eu tenho esses bytes, eu só preciso transformar eles em string, então transformo eles em string. 

Você poderia falar um encoding, tanto na desserialização, quanto na serialização.

O encoding poderia ser um parâmetro, o UTF8, etc., tudo isso você pode trabalhar a partir daqui, mas esse é o processo de desserialização, a gente sempre vai ter esses problemas independente de linguagem, só resta saber para qual tipo a gente que desserializar e escolher um tipo padrão.

Por padrão, a gente está desserializando então para a string, vamos testar? 

Como é que a gente testa? 

“Stopa” tudo e roda o log, o log está usando o service e o log está interessado em string mesmo. 

vamos ver se o log funciona.

Eu tenho aqui o cara que envia, o new order main e eu rodo. 

Deu um erro aqui, vamos dar uma olhada o que que foi, expected uma string, mas foi begin object. Opa, ele não está aparecendo, ele está esperando uma string? Ele está usando GsonDeserializer, etc., etc., vamos dar uma olhadinha aqui no meu Kafka service.

Meu Kafka service, olha aqui, key deserializer é uma string, value deserializer é um Gson deserializer, maravilha, agora que a gente terminou o Gson deserializer, a gente tem que tomar um cuidado, o que que acontece?

Se eu rodar o meu new order main, eu vou enviar tanto um order, quanto uma string. O order é um objeto Gson, o string é uma string pura, não é um objeto, isso quer dizer, o meu log service, que vai tentar transformar tudo isso daqui utilizando sempre o quê?

Um Gson, quando ele recebe do e-mail, que é uma string, não é um Gson... 

quando ele recebe uma string e não um Gson, ele vai se perder. 

Então, o que a gente teria que fazer é que quando eu quisesse, em algumas situações eu vou usar o Gson deserializer, em outras situações, eu quero utilizar outra coisa.

É um dos caminhos, se eu não quiser permitir... se eu quiser permitir, enviar, usar não somente o Gson deserializer. 

Uma outra maneira, é eu criar uma casca para um e-mail, assim como eu tenho um order, eu tenho uma casca para um e-mail e você vai ver que em 99,9% das vezes, a gente quer uma classe que representa uma mensagem, por que isso?

Porque uma string pura, um número puro, provavelmente tem mais informação ali, pensa num e-mail, o e-mail tem um corpo e tem o cabeçalho, então na prática, a gente quer ter aqui, não só isso, a gente quer ter um pouquinho mais, um e-mail a ser enviado, ele tem o subject e ele tem o body, ele tem as duas coisas.

Vou colocar aqui final, porque não é para alterar e vamos gerar o construtor, então a gente vai ter esses dois, o subject e o body. 

Então, o que que a gente faz agora? 

eu vou começar do zero, quando a gente envia new order, o new order vai despachar ou uma order ou um e-mail, ele despacha ou uma order ou um e-mail.

Agora que eu terminei o Gson deserializer, vamos parar para pensar, o log service, ele vai receber mensagens de diversos tipos e o Gson não vai ter como saber, “Ah, esse daqui é uma string”, “Ah, esse daqui é um objeto tipo order”, “Ah, esse daqui é um e-mail”.

Então quer dizer, por mais que no fraud detector service, a gente saiba que a gente quer uma order, a gente sabe que a gente quer uma order aqui, tem outras situações que a gente não sabe, tem situações que a gente não sabe, tem situações, tipo essa do log, que a gente pode receber qualquer coisa.

Então, a gente não está interessado no objeto, nesse caso em si, nesse caso específico, eu estou interessado simplesmente na string. 

Então, vamos tentar rodar o fraud detector service, vai enviar as mensagens.

Lembra, se por algum motivo você enviar e ela estiver num formato diferente agora, você pode começar a ter alguns conflitos, que mensagens antigas foram enviados com um tipo de objeto, a mensagem serializada de uma maneira e você está tentando serializar de outra, isso é bem comum de acontecer.

Então, eu vou tentar mandar a mensagem agora aqui, mandei as mensagens, vamos ver aqui no fraud detector. 

Opa, deu algum erro aqui, ele estava esperando uma string, mas recebeu um begin object, por que isso?

Porque... percebe que o new order main mandou orders, mas o fraud detector, em nenhum momento, ele falou aqui nos properties, que ele estava esperando um order, ele falou string. 

Então, de alguma maneira, quando a gente cria o nosso service, a gente tem que ser capaz de dizer uma configuração extra.

Qual é o tipo que eu espero de volta? 

O tipo que eu estou esperando é o tipo order.class, de alguma maneira a gente tem que ser capaz de falar isso, então de alguma maneira, eu vou fazer com que o meu construtor receba aqui o tipo class type.

Claro, ela é do tipo “T”, então eu vou passar isso daqui como parâmetro e a mesma coisa aqui, porque vai ser... todo o construtor precisa agora do type, a gente passa o type aqui, a gente muda a signature para ter o type.

E a gente fala aqui: “Na hora de criar os properties, usa o type e aqui no properties, recebe o class “T” type e a gente usa aqui o nosso type”. 

Esse método não vai poder mais ser estático, vou tirar ele daqui, para ele poder saber o tipo “T”, aqui eu passei o type.getName. Então, eu estou usando o tipo aqui.

Então, agora a gente está passando o tipo para o Kafka service, posso fechar, posso rodar o fraud detector service de novo. 

Opa, agora eu tenho que corrigir os outros lugares que tem que dizer qual é o tipo.

Então, por padrão, o e-mail vai estar continuar recebendo uma string e esse outro aqui também, o log, vamos deixar string por enquanto também, mas o que a gente está interessado em rodar? 

O fraud detector service, vamos ver se o fraud detector service está funcionando, depois a gente tem que corrigir o e-mail e o log.

Então o fraud detector falou: “Não posso fazer o cast de order para string por algum motivo, então de alguma maneira, quando a gente fez o run e a gente tentou fazer o parse, aqui, esse parse, ele recebeu o order e tentou transformar numa string, de alguma maneira ele tentou transformar numa string, por quê?

Porque a gente está passando aqui como string, a nossa função deveria ser de quê? 

De order. 

Repara que a gente não precisa mais desse order aqui, porque esse cara aqui recebe order, fechou, vamos lá de novo.

Então, agora a gente está usando o order como valor, acho que ele já tinha consumido as mensagens, talvez por isso ele não esteja rodando nada, então vou tentar agora aqui de novo mais 10 mensagens no new order main, envio 10 mensagens, 10 de order, 10 de e-mail e está aí, processando as compras, é uma order de verdade agora, que está sendo recebida.

Vamos fazer a mesma coisa para os outros, vamos fazer a mesma coisa para o e-mail e para o log, para o e-mail ficou fácil, porque é só a gente dizer: “O e-mail é uma string e acabou”, então se eu rodar o e-mail service do jeito que está, que vai receber uma string.

Então vamos ver lá, resetting offset, então ele está zerado, vamos rodar o new order main, ele tem que receber 10 valores aqui como strings e recebeu os 10 como string. 

Então o e-mail já está funcionando também. 

A grande questão é o log, porque no log, ele vai receber qualquer coisa, inclusive order, inclusive string, vai receber as duas coisas.

Vamos testar rodar ele uma vez, a gente testa rodar e agora a gente roda o nosso new order main, quando a gente rodar o new order main, a gente vai ver que ele pode se perder. 

Por que que ele se perde? 

Porque no log, a gente vai ter agora os dois tipos, a gente vai ter strings e objetos, strings tudo bem, mas objeto?

Aí, ele fala: “Não, objeto não, estava esperando uma string” e aí, da “caca”, então a gente precisa revisar como a gente faz o log.

OBS:
**o codigo neste momento nao esta funcional, gerando erros na leitura das mensagens no logService**.

KafkaService.java
```
class KafkaService<T> implements Closeable {
    private final KafkaConsumer<String, T> consumer;
    private final ConsumerFunction parse;

    KafkaService(String groupID, String topic, ConsumerFunction parse, Class<T> type) {
        this(parse, groupID, type);
        consumer.subscribe(Collections.singletonList(topic)); // inscriçao nos topicos ouvidos
    }

    KafkaService(String groupID, Pattern topic, ConsumerFunction parse, Class<T> type) {
        this(parse, groupID, type);
        consumer.subscribe(topic); // inscriçao nos topicos por regex
    }

    private KafkaService(ConsumerFunction parse, String groupID, Class<T> type) {
        this.parse = parse;
        this.consumer = new KafkaConsumer<String, T>(properties(type, groupID));
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

    private Properties properties(Class<T> type, String groupID) {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());// deserializador da chave
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, GsonDeserializer.class.getName()); // deserializador de mensagens
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupID);// consumer group name
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());// client name
        properties.setProperty(GsonDeserializer.TYPE_CONFIG, type.getName());

        return properties;
    }

    @Override
    public void close() {
        consumer.close();
    }
}
```

FraudDetectorService.java
```
public class FraudDetectorService {
    public static void main(String[] args) {
        var fraudService = new FraudDetectorService();
        try(var service = new KafkaService<Order>(
                FraudDetectorService.class.getSimpleName(), // group
                "ECOMMERCE_NEW_ORDER", // topic
                fraudService::parse, // parse function
                Order.class // expected type of message
        )) {
            service.run();
        }
    }

    private void parse(ConsumerRecord<String, Order> record) {
        System.out.println("-----------------");
        System.out.println("Processando new order, checking for fraud");
...
```

LogService.java
```
public class LogService {
    public static void main(String[] args) {
        var logService = new LogService();
        try (var service = new KafkaService(
                LogService.class.getSimpleName(),
                Pattern.compile("ECOMMERCE.*"),
                logService::parse,
                String.class)) {
            service.run();
        }
    }
...
```

EmailService.java
```
public class EmailService {
    public static void main(String[] args) {
        var emailService = new EmailService();
        try (var service = new KafkaService(
                EmailService.class.getSimpleName(),
                "ECOMMERCE_SEND_EMAIL",
                emailService::parse,
                String.class
        )) {
            service.run();
        }
    }
...
```

GsonDeserializer.java
```
public class GsonDeserializer <T> implements Deserializer<T> {

    public static final String TYPE_CONFIG = "br.com.alura.ecommerce.type_config"; // uma string qualquer. vai ser overriden pelo tipo passado
    private final Gson gson = new GsonBuilder().create();
    private Class<T> type;

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        String typeName = String.valueOf(configs.get(TYPE_CONFIG));
        try {
            this.type = (Class<T>) Class.forName(typeName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Type for deserialization does not exist in classpath", e);
        }
    }

    @Override
    public T deserialize(String s, byte[] bytes) {
        return gson.fromJson(new String (bytes), type);
    }
}
```

ConsumerFunction.java
```
public interface ConsumerFunction<T> {
    void consume(ConsumerRecord<String, T> record);
}
```

### Lidando com customizações
Repara então, que o nosso problema agora é... 

tem um momento em que a gente quer usar vários subjects, se a gente não sabe o que está vindo. 

Lembra que eu citei, isso é raro, isso é só quando você não tem um padrão específico para a sua mensagem.

Então, tem uma situação em que isso acontece que é tipo um log, tipo alguém que recebe mensagens com erro, algo do gênero, então quando isso acontece, o que que você tem que fazer?

Você tem que de alguma maneira dizer: “**Eu não quero usar esse desserializador aqui, eu quero usar o meu string deserializer e não o GsonDeserializer**”. 

Então tem várias maneiras de fazer isso.

Na verdade, o que a gente quer fazer é que quando eu crio um Kafka service, eu queria ser capaz de passar diversas propriedades extras, para cada serviço, de repente eu quero customizar, aí tem várias maneiras de fazer isso, você pode criar um builder, etc... Não vou criar um builder, vou manter o construtor.

Você poderia falar: “Ah, agora eu já quero criar um builder, porque tem mil combinações, etc.”, eu estou ainda com duas basicamente, basicamente, tem uma virgula aí, mas basicamente duas. 

Se basicamente duas para mim é o suficiente, pessoalmente eu vou manter os dois construtores, acho muito melhor do que criar um builder só para isso.

Então vamos lá, o que que eu vou querer fazer aqui? 

Eu vou passar um outro argumento, um argumento que vai me dizer um mapa, como se fosse um properties, que vai me dizer propriedades extras de configuração do Kafka.

Então existe no Java, um Map.of, qualquer linguagem, você cria como você quiser, é um mapa que você quiser de propriedades extras, consumer config, eu vou falar aqui o value de serializer, nesse caso específico, ele é o string deserializer.class.getName, é isso.

Vamos importar e está ai, então eu estou passando agora configurações extras, se eu precisar mais, é só colocar uma vírgula e sair adicionando nesse mapinha, quantas configurações a mais você quiser. 

Claro, o construtor, nesse instante, não suporta isso, então a gente tem que fazer o quê?

#### Propriedades extras de configuraçao do Kafka service, dependendo de quem a cria
A gente tem que mudar o construtor para receber um mapa, então o nosso construtor aqui do Kafka service vai ter que receber um mapa de string para string, que são propriedades extras. 

Essas propriedades são opcionais ou obrigatórias? 

O que que você vai querer, opcional ou obrigatória?

Para pra perceber, dá para eu criar um mapa assim, vazio? 

Um map.of? 

Eu até posso criar esse map.of, o problema é o tipo, ele não vai saber direito qual tipo ele está criando, mas você pode.

Então, como é fácil de criar a propriedade vazia, eu prefiro falar: “Eu quero obrigatório”, você vai me dizer quais são um mapa de string para string, que são propriedades extras, então você vai me passar isso e é claro, eu vou passar isso para as minha propriedades.

Então, eu vou alterar o meu construtor aqui para receber essas propriedades, que é esse cara aqui e na hora que eu chamo o properties, eu passo essas properties também, getProperties e aí, eu passo as properties.

Então, getProperties e eu recebo também um mapa de string, string que são as properties e o que que eu quero fazer? 

Dado que eu tenho as minhas properties padrão aqui, tenho essas daqui, eu vou colocar umas aqui, overrideProperties, são as que eu vou sobrescrever.

Então, o que eu vou fazer agora é, eu pego essas properties que eu tenho e eu falo: “Properties, coloca aí dentro tudo o que está no override”, então tudo o que você sobrescreveu, eu sobrescrevo.

Então, dessa maneira, eu acabei de permitir esse tipo de configuração extra. 

Agora, todos os services tem que ter isso, então no fraud detector service, precisa? 

Precisa, eu falo: “Um mapa de string” e pronto.

Aí, você fala: “Ah, esse mapa de string é opcional?”, é opcional aqui e ele se vira, então se você quiser um mapa vazio, new HashMap ou como eu tinha dito antes, um Map.of, vazio. 

Só que se eu fizer aqui um Map.of vazio, fica assim, Map.of vazio, o que você achar que faz mais sentido, um mapa novo ou um Map.of vazio.

O que você acha que fizer mais sentido a legibilidade no caso do Java, muitas linguagens você vai escrever simplesmente assim, tem linguagens que tem parâmetro padrão, então só colocam com igual padrão, lá dentro alguma coisa que faça sentido e por aí, vai.

O que você achar que faz sentido, mas para mim, resolvi o meu problema, com pouquinho código, eu mantive o controle nos construtores, tenho ainda só dois construtores, está super controlado e não tenho esse problema. 

Então, não tive que criar um builder que tem mil combinações, é mutável, nada nisso, só usando construtor.

Então, vamos testar, tem que testar tudo. 

Então, o fraud detector service está rodando, o e-mail service está rodando; o log service e ele deve usar agora o desserializer de uma string, não me importa se é Gson o que que é, eu só quero saber a string que está aí dentro, porque é só para logar.

Então, se a gente rodar de novo o new order main, ele vai jogar mensagens em todos esses serviços, então com isso, a gente criou uma biblioteca, nossa, própria, que é capaz de customizar os serviços de acordo com as propriedades que a gente quer passar.

Então, a gente sugere essa camada própria em qualquer linguagem que você vá criar.

OBS:
**codigo novamente executavel**

LogService.java
```
  public static void main(String[] args) {
        var logService = new LogService();
        try (var service = new KafkaService(
                LogService.class.getSimpleName(),
                Pattern.compile("ECOMMERCE.*"),
                logService::parse,
                String.class,
                Map.of(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName())
        )) {
            service.run();
        }
    }
...
```

KafkaService.java
```
class KafkaService<T> implements Closeable {
    private final KafkaConsumer<String, T> consumer;
    private final ConsumerFunction parse;

    KafkaService(String groupID, String topic, ConsumerFunction parse, Class<T> type, Map<String, String> properties) {
        this(parse, groupID, type, properties);
        consumer.subscribe(Collections.singletonList(topic)); // inscriçao nos topicos ouvidos
    }

    KafkaService(String groupID, Pattern topic, ConsumerFunction parse, Class<T> type, Map<String, String> properties) {
        this(parse, groupID, type, properties);
        consumer.subscribe(topic); // inscriçao nos topicos por regex
    }

    private KafkaService(ConsumerFunction parse, String groupID, Class<T> type, Map<String, String> properties) {
        this.parse = parse;
        this.consumer = new KafkaConsumer<String, T>(getProperties(type, groupID, properties));
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

    private Properties getProperties(Class<T> type, String groupID, Map<String, String> overrideProperties) {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());// deserializador da chave
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, GsonDeserializer.class.getName()); // deserializador de mensagens
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupID);// consumer group name
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());// client name
        properties.setProperty(GsonDeserializer.TYPE_CONFIG, type.getName());

        properties.putAll(overrideProperties);//sobrescreve as propriedades
        return properties;
    }

    @Override
    public void close() {
        consumer.close();
    }
}
```

FraudDetectorService.java
```
public class FraudDetectorService {
    public static void main(String[] args) {
        var fraudService = new FraudDetectorService();
        try(var service = new KafkaService<Order>(
                FraudDetectorService.class.getSimpleName(), // group
                "ECOMMERCE_NEW_ORDER", // topic
                fraudService::parse, // parse function
                Order.class, // expected type of message
                new HashMap<String, String>()//cria um mapa vazio que nao vai ter nada para override nas propriedades
        )) {
            service.run();
        }
    }
...
```

EmailService.java
```
public class EmailService {
    public static void main(String[] args) {
        var emailService = new EmailService();
        try (var service = new KafkaService(
                EmailService.class.getSimpleName(),
                "ECOMMERCE_SEND_EMAIL",
                emailService::parse,
                String.class,
                Map.of()//cria um mapa vazio que nao vai sobrescrever nada nas properties
        )) {
            service.run();
        }
    }
...
```

### O que aprendemos?
* Como limpar os diretórios de log e dados do zookeeper e kafka
* Como utilizar diretórios não temporátios para o zookeeper e kafka
* Como utilizar o GSON
* Criando um serializador customizado do Kafka
* Verificar o conteúdo exato de uma mensagem em um programa
* Deserialização customizada
* Lidando com customização por serviço

## Microserviços e módulos
### Microsserviços como módulos em um mono repo
Agora que a gente já tem o nosso projeto rodando, para pra pensar, cada um desses serviços, o e-mail service, fraud detector service, o log service e até mesmo um new order main, eu posso considerar como um serviço isolado, cada um desses serviços não tem nada a ver um com o outro.

Existe uma coincidência de que um envia uma mensagem e outros estão “escutando” a coincidência e existe um esquema, que é uma estrutura de como a mensagem deve ser escrita para que os dois lados consigam se comunicar, mas um lado não conhece exatamente o outro.

Então faz sentido esses lados estarem dentro do mesmo projeto? 

Tem gente que vai argumentar que sim, tem gente que não. 

Se a gente colocar tudo dentro do mesmo projetão, a gente vai estar meio que com um grande monólito, pode até ser, apesar de a gente ter quebrado em vários serviços e rodar eles isoladamente.

O que que costuma ser feito? 

Como cada serviço tem suas próprias dependências, as vezes você tem que cada um desses pequenos serviços, desses micro serviços tem o seu banco de dados. 

Então, eu tenho o meu banco de dados, você tem o seu banco de dados, cada um com dados distintos, partes distintas das informações.

Eu tenho acesso ao estoque, você tem acesso aos dados pessoais de um usuário, eu como pessoa de estoque, não quero ter acesso a dados de senha do meu usuário, porque é uma questão de segurança, não faz sentido, bancos distintos, bancos pequenos, isolados.

Então, cada serviço pode ter uma infraestrutura diferente, apesar de, claro, terem dependências muito comuns, como por exemplo, o Kafka, já que a comunicação está ocorrendo via Kafka. 

Então o que que é comum?

Que a gente separe isso em projetos, como que eu separo isso em projetos?

Usa a ferramenta que a sua linguagem tiver disponível para você, no nosso caso em Java, é bem comum usar maven, gradle ou alguma outra ferramenta e a gente submódulos, você também poderia criar projetos distintos e ponto final.

A vantagem de a gente criar submódulos aqui dentro do nosso projeto, aqui a gente consegue comitar tudo isso, também poderia, mesmo sem submódulos, dentro do mesmo git, dentro do mesmo repositório.

Então, se eu comito tudo dentro do mesmo repositório, todos os meus projetos, eu tenho um monorepo, que é uma abordagem, um grande um mono repo, repositório. 

Então é uma abordagem que todo mundo, de todos os times tem acesso a todo código.

Então, se suas questões de seguranças internas permitem isso, é uma abordagem, não necessariamente a única. 

Então é isso que eu vou fazer aqui, eu vou criar um módulo novo.

Você está em outra linguagem, quer programar em outra linguagem, em outro projeto, seja lá o que for, usa a ferramenta que você quiser, crie outros projetos, outros módulos, o termo que for.

Repara que ele segue, no caso do maven e do gradle, ele segue o mesmo nome do parent, o groupId, a sugestão é sempre manter a mesma e o artifactld, aí sim, o artifactld, eu vou chamar de service e-mail, podia chamar de e-mail service? Podia, só que daí, cada serviço ia ficar com... meio que bagunçado, se eu tiver outros nomes.

A vantagem de colocar o service aqui no começo, para mim, é que vai organizar, vai ficar todos os services juntinhos e aí, se eu tiver bibliotecas comuns, eu vou chamar de commons libraries, aí vai ficar separadinho, commons libraries e depois o services ou (common) http ou (common) Kafka, (common) não sei o quê.

Então eu agrupo, fica ordenado por ordem alfabética. 

Então, service e-mail e o padrão, no meu caso, é com o hífen, por causa do “e” do maven. 

#### Criando um novo modulo no maven
Então, next, deixo ele criar para mim o meu projeto, o meu submódulo, ele já criou aqui um pom separado, aqui dentro do service e-mail.

E o pom do pai ou da mãe do parent, já fala: “Tem um módulo chamado service e-mail, então aqui eu tenho o meu service e-mail, por padrão, ele tem as dependências desse cara aqui, então ele já ganhou o Kafka, o slf4j e o Gson, esse é o meu service e-mail.

Tem o diretório Java lá dentro, então o que que eu quero mover para lá, o e-mail service, eu quero mover o e-mail service para lá, então eu vou clicar na direita, refactor, move.

Eu prefiro fazer isso do que arrastar, eu acho mais fácil o passo a passo aqui, quando eu mover de um projeto para outro, eu prefiro... o intelliJ, outras ferramentas, tem alguns problemas em algumas situações, em algumas linguagens, no move, eu prefiro fazer com super calma.

Então eu quero manter o mesmo pacote, eu prefiro com a janela de diálogo com calma... manter o mesmo pacote, olha o target directory, o destino, o meu destino vai ser esse daqui, service e-mail, o diretório Java dele, então ok, refactor.

Então, ele vai falar assim: “Guilherme, veja lá, o e-mail service main, não vai estar mais acessível nesse módulo”, faz sentido, o e-mail service está indo para lá, não vai estar mais aqui, vai estar ali, faz todo o sentido, pode ir. 

Então, teoricamente, o e-mail service está aqui agora.

Aí, você fala: “Mas ele não está encontrando o Kafka service”, por que que não está encontrando o kafka service? 

Calma lá, o pom, ele adicionou as dependências que estão aqui, ele não adicionou o código que está aqui.

Então, se a gente for lá no nosso e-mail service e adicionar dependência no módulo pai, então a gente quer adicionar uma dependência no módulo pai, é isso que eu quero.

Adicionei a dependência, então se a gente der uma olhada agora no nosso pom, a gente vai falar: “Tem a dependência do módulo pai”, está no escopo de compilação? 

Não, vai precisar no escopo de rodar mesmo, então no escopo de rodar, no caso do maven, quando eu for rodar, eu vou precisar.

Está aqui, importou, eu tenho o meu e-mail service separado, num módulo separado bonitinho, controlado e ele só usa as classes que estão nele e nesse outro projeto. 

Aí, você fala: “Legal, Guilherme, então a gente fez isso, mas lembra que eu posso criar então os outros módulos, dos outros serviços?

Vamos criar, os outros módulos são rápidos agora, a gente vai ter um e-mail... um service de fraude, então um service fraud detector. 

Hífen, fraud detector, crio sem segredo, mesma coisa, o que que eu tenho que mover para lá? O fraud detector service. Refactor, move.

Então, na caixa de diálogo, por isso que eu acho que é importante, porque a gente precisa lembrar de clicar no reticencias, escolher aqui direitinho o diretório, para evitar dar alguma “cáca” aí. O refactor, ele vai sugerir o que que vai acontecer de errado, tem um errinho a mais, eu quero que a gente veja esse errinho a mais.

O fraud detector está lá e olha aqui o fraud detector service. Aqui o fraud detector service, não está dependendo da classe de lá, mas o order, a gente também depende do order agora. Então, eu vou adicionar dependência.

Então, agora que a gente está dependendo, trouxe tudo, você fala: “Beleza, Guilherme, trouxe tudo”. Vamos agora para o próximo? Então o próximo módulo: log service. Então, o novo módulo, repara, agora fica rápido, os módulos de serviços são rápidos de criar.

Opa, service log, finish, a gente vai pegar o log service.

Então no service log, vamos refatorar, continue e a gente tem o nosso service log no pacote adequado e a gente já faz a nossa dependência. 

Vou fechar enquanto ele corrige. Aí, você fala: “Sobrou mais um serviço?”.

Sim, serviço de new order, o serviço de new order, ele é um método main, assim como os outros serviços, então, novo módulo. 

novo módulo, um service new order, ele é um método main, como os outros serviços. 

E a gente vai mover pra cá, o new order, refactor, move, a new order.

Vamos corrigir, aqui, ele tem várias coisas que ele depende. 

Sobraram várias classes, Consumer Function, Gson deserializer, Gson serializer, Kafka dispatcher e Kafka service, cinco classes que são relativas ao Kafka, certo?

São as classes comuns, bibliotecas comuns, que dão base para o nosso trabalho com o Kafka, para meio que esconder o Kafka para programadora ou programadora final. 

Então, eu vou criar um novo módulo agora, um novo módulo, para separar bonitinho, vai se chamar “common”, de biblioteca comum, assim como tem o apache commons.

Então, common e aí, Kafka, que são as classes que trabalham com o Kafka. 

Então, o nosso common-kafka, o que que a gente vai ter lá dentro? 

A gente vai ter essas várias classes, exceto a order, a order não tem nada a ver com o kafka, a order é do nosso projeto.

Então vamos ver, lembra para o diretório do common-kafka, aqui refatorei. 

Porque o order não depende daquilo lá, não tem problema, mas o que que acontece?

Todos os outros que a gente tinha colocado dependência para esse cara aqui, na verdade dependem do common-kafka agora. 

Aqui, a gente depende do common-kafka, então o que a gente pode fazer? 

“Ctrl + Enter”, adicionar dependência do common-kafka.

Adicionei dependência do common-kafka? 

Maravilha. 

Então, em cada um dos serviços, na verdade, invés de adicionar a dependência da raiz, eu vou adicionar dependência das bibliotecas que fazem sentido, então é do common-kafka. 

acredito que foram todos. 

Então, olha o service e-mail, o fraud detector, aqui é o commond, o log e Log service.

A classe new order main, ele já está importada. 

Então, agora a gente está dependendo do common-kafka, então esses projetos estão dependendo do common kafka, a gente já isolou uma biblioteca comum, que de repente é uma outra equipe, que é responsável por fazer isso, o principal no dia-a-dia.

A gente até tem acesso, porque está no nosso repositório, mas os serviços estão isolados.

Qual é a única coisa que é comum a esses quatro serviços, que está na raiz? 

Que todos eles estão acessando, que é comum e que pode ser um perigo? 

É o order.

OBS:
**ver commit para ver as alteraçoes necessarias**

### Binários dos microsserviços
Chegou na hora de a gente falar na classe order, qual que é a diferença da dependência desses projetos, para uma classe do tipo Kafka dispatcher e a classe order? 

O problema é o seguinte, se eu um desses serviços é lançado com uma versão do Kafka dispatcher e o outro com outra versão do Kafka dispatcher, não tem problema, por quê?

Porque se um está despachando a mensagem e o outro está recebendo mensagem, mas ambos estão usando o Kafka, tanto faz, certo? 

Porque está indo o subject, está indo o body, está indo tudo. 

Agora, o order não, o order define a mensagem, o formato da mensagem, o conteúdo da mensagem, tudo depende dessa classe order.

Então quer dizer que, se eu tenho uma classe order, que é compartilhada entre todos os projetos que utilizam o order, a mesma classe order, então um JAR, por exemplo, um Zip qualquer, de qualquer formato de programação de linguagem... que é compartilhada entre outros... todos eles, funciona.

Só que o que que acontece? 

Todos eles vão usar um order que userId, orderId e amount. 

Talvez um desses projetos precisasse de um campo a mais ou tu precisasse de um campo a menos ou tu não sei o quê.

A representação de um pedido de compra, para cada um dos serviços, poder ser um pouco diferente e do jeito que a gente está fazendo, a gente está forçando goela abaixo. 

Então, quer dizer, se um desses serviços precisar de uma coisa nova, vai ter que criar a coisa nova aqui.

E provavelmente, talvez, depende de como implementado o serializer e o deserializer, a gente tenha que atualizar todos os outros serviços e lançar todos os outros serviços ao mesmo tempo e isso não permite a evolução dos serviços de maneira independente.

Imagina: “Ah, eu terminei aqui o meu código, eu quero lançar”, você tem que esperar aquele outro pessoal corrigir um bug que está na branch principal, porque eles também estão usando a classe order, só que para lançar o deles, precisa atualizar, que está com um erro ainda.

E aí, você vai ter que ficar esperando e aí, você fica esperando e aí, um espera o outro. 

E aí, se tem um lugar que tem 300 projetos, todo mundo está sempre esperando alguém. 

Estou dando um exemplo de 300 micro serviços, claro. 

Então é uma abordagem complicada, ter esse arquivo compartilhado com todo mundo.

O que que costuma ser feito? 

Invés de a gente ter esse arquivo compartilhado com todo mundo, quem precisa de uma order, tem a sua própria order. 

Então, vamos ver quem precisa de uma order, o e-mail precisava de order?

Não precisava, então o que que a gente pode fazer? 

Se não precisa, a gente remove a dependência, quem mais? 

O fraud detector, o fraud detector recebe uma ordem, então ele precisa. 

Quem mais? 

O service log, o service log lida com string, não precisa, ele usa string deserializer.

new order precisa, então sobraram dois projetos, o fraud detector e o new order, que precisam de order. 

O que que eu vou fazer? 

Eu vou pegar nos dos projetos, a gente tem o pacote br.com.alura.ecommerce e aí, eu vou mover esse daqui para cá, movi.

Refactor, continue, movi. 

Se eu movi para o fraud detector, o fraud detector continua ok, inclusive eu posso tirar agora a dependência. 

Então beleza, desse daqui está ok, só que se a gente abrir agora, o new order main, não tem mais a classe order. 

Então, eu vou copiar e vou colar a classe order aqui, aí você fala: “Guilherme, mas então, você ficou com um copy paste de uma classe de modelos simples”, anêmica.

Então, quando a gente fala orientação objetos, só tem dados, que lembram estruturas de dados, é exatamente isso o que eu tenho aqui, que é uma má prática. Só que essa classe está sendo utilizada para que nesse instante?

Só como uma maneira, uma casca de serialização, desserialização, parece um data transfer object, para quem está acostumado com o mundo de design patterns e transferência de objetos, é isso que está lembrando um data transfer object.

Uma maneira de eu serializar e desserializar e enviar esses dados de uma camada para outra, é isso, de eu garantir o esquema com certas características. 

Claro, Gson serializer e deserializer tem que ser implementado de maneira com aquilo que você quer, para a serialização e a desserialização.

Quero campos opcionais, não quero campos opcionais, etc. 

Beleza, faz o processo de serialização e desserialização que você quer, mas agora, a gente tem então essa duplicidade dessa classe simples e boba e é isso que a gente vai trabalhar, por quê?

Porque talvez em uma, a gente precise de mais campos, em outra de menos, não tem problema. 

É claro, o desserializador precisa ser capaz de lidar com campos que eu não me interesse, com campos que faltam, então eu jogo uma exception, aí você vai fazer a medida do que precisar.

Isso é um assunto deparado, que é uma questão de serialização, desserialização, que a gente toca em cursos do gênero. 

Então, não sobrou nada aqui? Eu posso apagar esse diretório até, porque não sobrou nada, então eu posso apagar esse diretório, o que mais?

Eu posso dar uma batida nos pontos, agora ninguém mais depende daquele principal e a gente tira o compile porque, na verdade, é tudo dependência para valer.

O que que falta? 

Testar tudo isso. 

No service e-mail, então eu vou rodar ela aqui.

O próximo, além do e-mail é o fraud detector service. 

Vamos rodar o log service.

falta a gente enviar 10 pedidos de compra, porque agora que a gente tem aqui todo mundo rodando.

tem aqui no log service as várias mensagens, no fraud detector rodando e no e-mail service... agora, começou a rodar e enviar os e-mails. 

Então, tudo isso está rodando. 

Se você está num projeto do tipo maven, gradle ou outra ferramenta de Java.

E se você está em outra linguagem, o que que você pode querer fazer agora? 

Gerar todos esses seus projetos. 

Claro, no maven, a gente usa o mvn package, que gera o pacote de um projeto. 

Então, eu vou aqui no projeto principal, eu abro aqui o meu maven do principal, que esse projetão, que tem vários módulos.

E aqui em cima a direita tem um maven, então no maven, eu posso escolher o e-commerce, que é a raiz e eu posso pedir para rodar, eu quero rodar esse cara, eu queria rodar. Para rodar, eu tenho aqui um maven goal, qual é o goal que eu quero rodar?

Eu quero rodar o package. 

Então, ele está rodando um mvn package, para quê? 

Para gerar o pacote desse projeto. 

Calma aí, ele está gerando de quem?

Ele está gerando do Kafka JAR, então ele vai gerar o Kafka, ele vai gerar o JAR dessa biblioteca aqui, dessa daqui. 

Depois ele vai gerar o JAR do service e-mail e por aí, vai. E aí, ele começa a gerar o JAR de cada um desses caras.

Ele falou: “Gerei o JAR do e-commerce, o JAR do common-kafka, o JAR do service e-mail, do service fraud detector, do service log e do service new order. 

Claro, o e-commerce é só um pom, é só um parênteses, é só um pai que não tem código no nosso caso, mas todos os outros, ele gerou o JAR.

Então, quer dizer, se a gente está trabalhando com submódulos e um projeto com micro serviços, várias serviços dentro do maven ou de outra ferramenta em qualquer linguagem, você vai ter um comando, no caso do maven, mvn package, que você roda e já gera todos os seus JAR, todo os seus serviços, faz o build de todos os serviços.

Claro se você quiser criar... rodar o build de só um serviço, você pode ir lá no maven ou na sua ferramenta que você utiliza e rodar só para ela, não tem problema nenhum. 

Então com isso, a gente tem aí, todos os nossos JARs sendo criados e todas as bibliotecas e a gente pode fazer deploy como a gente quiser.

### O que aprendemos?
* como criar módulos
* como manter tudo em um mono repo
* como gerenciar dependências entre módulos
* como gerar os binários de cada módulo

# kafka: fast delegate, evoluçao e cluster de brokers
## novos produtores e consumidores
### Produtores consumidores e o eager de patterns
A pessoa gerou uma venda. 

Então eu tenho um serviço que gera 10 vendas para testar e essas 10 vendas geram 20 mensagens 10 de e-mail e 10 de novas vendas, novos pedidos de venda. Esses pedidos de venda passam por um detector de fraude e o e-mail para service mail. 

Todas as mensagens são logados no service log.

Repara que tem serviços enviando e serviços escutando mensagens. 

Vamos agora misturar as duas coisas, mostrar que não tem desafio em misturar - enviar e receber mensagens no mesmo serviço.

Vamos pegar o serviço de detecção de fraudes que ele recebe a mensagem de um novo pedido, se ele recebe mensagem de um novo pedido no Java dele ele espera 5 segundos e a ordem foi processada - deu certo ou não deu não importa.

Eu queria simular uma situação onde em alguns casos esse nosso pedido foi aprovado e em outros não, teve uma fraude. 

Quando gera os pedidos de compra, o valor é um valor de um a cinco mil mais ou menos.

Você poder fazer assim, se valor for acima de quatro mil e quinhentos recusa. 

Na prática se você está usando uma inteligência artificial, algoritmo que for você vai deixar seu estimador nesse serviço, a equipe da machine learning implementa e deixa aqui.

Vou fazer uma regra simples para tentar identificar a fraude - a fraude é seu pedido é muito caro, é fraude não. 

Isso não é uma coisa do mundo real esse detector de fraude, que não é o foco desse curso. 

Esperei esses 5 segundos para simular esse algoritmo lerdo, e quero pegar essa order, acessá-la no record, o var order = record.value dela devolve uma ordem para mim. 

Agora eu posso fazer o que eu quiser com essa order. 

Será que o valor dessa order, order.get, get o que? 

O meu get amount eu gostaria de comparar, gostaria de saber se ele é maior ou menor. 

Então queria saber se é greater ou lesser, mas não tem. No BigDecimal o que tem é compareTo.

Eu passo outro BigDecimal, vou passar com aspas que eu tenho a precisão de que seja “4500” exatamente. 

Se for maior ou igual a zero, quer dizer que o preço é muito alto é maior que 4500, pretending the fraud happen when the amount is >= 4500, maior ou igual a 4500.

Nesse caso teve uma fraude. 

Eu acho legal mesmo que a lógica seja super simples, extrair em Um método. 

No IntelliJ Refractor Extract Method, quero saber se é fraude isFraud.

Aproveitaria o método de fraude aqui no caso no meu serviço ou nas classes que fizerem sentido para o seu serviço. 

Se for uma Fraude que eu vou fazer? 

Em vez do colocar order processed, vou colocar order is a fraud.

Se não ela não é, quer dizer ela não é uma fraude, System.out eu vou falar approved: + order, posso colocar aqui aprovado. 

Repara que agora a minha classe order é diferente da classe order que envia.

Não tem problema, porque o processo de serialização e desserialização só usa os campos. 

Lembra que eu falei importância no outro curso, de que o processo de serialização e desserialização pode ser feita de diversas maneiras, da maneira que estou fazendo agora se um serviço precisa de certos métodos e outro de outro não tem problema nenhum.

Não precisa ficar colocando dependências nos dois, misturando e “sujando” um projeto com coisa do outro projeto. 

Eles são isolados, tem um get que é um método simples, ainda continua anêmico etc., mas poderia colocar outros métodos.

Vou rodar o meu fraud detector, run fraud detector, vou agora no meu New Order Main, vou tentar rodar também.

Vou rodar primeiro meu fraud detector. 

Vou mandar 10 mensagem de e-mail, 10 mensagens de New Order e vemos as new orders chegando e checando pela fraude. 

Checou pela fraude aprovou essa e ele vai aprovando ou recusando de acordo com o valor, ele vai imprimindo.

É uma fraude e quando é sucesso ele fala aprovado, só que ele não está falando dados de aprovado ou os dados do que foi uma fraude, eu queria imprimir aqui, quando imprimo realmente a ordem, eu queria substituir também o to string, poder mostrar a order por completo então no caso do Java pode gerar to string muito fácil aqui.

Cada linguagem, cada ferramenta vai ter uma maneira.

Então vou mandar mensagem nova, as 10 mensagens e vai começar a ver agora as orders corretas. 

Então essa daqui que o amount é 2716 tem ser aprovada e depois tem outra 2500 aprovada. 

E por aí vai, cada uma delas vai sendo aprovada ou recusada.

O que eu queria era que o meu serviço além de ter tem essa vontade de evoluir independentemente do outro serviço, eu posso agora enviar mensagens também. 

Além de eu receber mensagens, eu gostaria de enviar mensagem. 

Se eu quero enviar mensagem preciso de um Kafka Dispatcher.

Como estamos dentro do FraudDetectorService vou colocar private final KafkaDispatcher que ele vai despachar se é o pedido que foi aceito eu poderia despachar o pedido, a minha order.

OrderDispatcher é meu KafkaDispatcher do tipo order, como já está definindo aqui o tipo, não precisa do tipo aqui e temos um dispatcher agora. 

Com order dispatcher eu posso despachar mensagens, se foi, por exemplo, recusado OrderDispatcher.send, tópico e-commerce, minha order fraudulenta, então foi rejeitada, reject. 

Poderia ser fraude, o que fosse, a chave que estou ID do usuário get, não tem o ID do usuário, user ID, e qual que é o objeto que vai enviar? A própria order.

Faltou criar o get user ID, vamos lá no nosso order, o getUserId, criei, ele está aqui. 

Tanto no envio do ecommerce rejected e do ecommerce order approved, nesse caso foi aprovada a minha order. 

Ele está reclamando das exceptions, eu tenho que jogar as exceptions que podem ocorrer, adiciono as exceptions na minha função. 

Dá um erro, porque a função joga exception, a nossa função de passa joga exception. 

O KafkaService recebe um consumer function que não pode jogar exception.

tem que tomar cuidado. vamos ter de adicionar as exceptions aqui. 

Vou adicionar o mínimo, prefiro ir sempre pelo caminho do mínimo, vai adicionando na medida do necessário. 

então agora no ConsumerFunction pode dar ExecutionException ou InterruptedException, vamos saber o que fazer. Dá uma olhada no KafkaService, quando chama consume quer tratar esse erro. 

Se deu esse erro, preciso tratar.

Ou eu jogo exception ou eu paro meu serviço completamente, o caso da exception ou eu trato a exception para essa mensagem e a próxima eu continuo trabalhando.

tem várias opções para fazer o tratamento, por enquanto você só vai logar. 

então, so far, Just logging the exception for this message, em algum lugar armazenar as mensagens que deram erro. 

Poderia não comentar essa mensagem e deixa tentar de novo, enquanto está dando exception, tem várias maneiras de lidar com esse erro e focar na hora de falar apenas de tratamento de erro.

Por enquanto que eu queria era ser capaz de um serviço que recebe, também enviar mensagens e é isso que eu estou fazendo. 

Mas o que falta? 

Rodar o log, vou restartar FraudDetectorService, vou abrir meu LogService e rodar para ver as mensagens todas.

Quando envia 10 e-mail, 10 da compra, do pedido de compra que chega aqui. 

Esses 10 do pedido de compra vão virar 10 novas - seja de aprovação ou de rejeição. 

Então 30 mensagens enviadas de um lado para o outro, rodando NewOrderMain, todas elas chegam aqui do send email, todas do FraudDetector chegam no log, daqui a pouquinho FraudDetector começa a rodar começa a rodar e fala enviei.

E o LogService não está recebendo, um cuidado muito importante quando está trabalhando com o LogService com pattern; quais são os subjects que ele está escutando não é dinâmico, não é que enquanto ele está escutando se surgiu subject novo ele escuta, não.

Ele começa a escutar o subject na hora que você roda ele, os subjects que têm, que servem esse padrão são subjects saber se ele vai escutar. 

**Se surgir um novo subject que segue esse padrão, não vai escutar** e surgiu um novo subject que não existia antes, dois novos surgiram e foram enviados.

Isto é, ele não tava escutando. 

Mas agora como esses tópicos já existem podemos rodar de novo o LogService e rodar o NewOrderMain.

Agora sim LogService vai pegar todas as mensagens dos tópicos que já existem, que são aqueles dois de ecommerce send mail e outro e do e-comerce approved e do e-commerce rejected (esse tem de esperar ocorrer aqui, de vez em quando acontece); 

fizemos um consumidor que também é produtor.

### Um serviço que acessa bancos externos
Nosso próximo passo é criar um novo serviço. 

Eu queria mostrar agora essa questão da independência dos projetos, tem uma certa dependência de acordo com a estrutura do esquema das mensagens que são enviadas aquele JSON enviado de um lado para o outro.

Porém as nossas dependências internas ao serviço são independentes. 

Vamos observar serviço novo, que vai utilizar algo a mais: um banco de dados. 

Um outro serviço externo, eu quero criar um novo serviço que toda vez que vem uma mensagem de um pedido de compra novo, se o usuário é uma pessoa nova, tem um e-mail novo eu vou inserir esse usuário no banco.

A maneira de fazer isso é criar um serviço que representa o banco de usuários, onde teria as informações pessoais dos usuários e eu não gostaria que todos serviços acessassem.

Eu vou criar um novo módulo, Esse módulo eu vou chamar de service-users, que é onde estão os usuários. 

#### SQLite
Se eu vou utilizar uma quantidade eu preciso de banco de dados e o que vamos utilizar se chama mvnrepository sqlite.

Quero a versão sqlite para o Java, então vou usar versão 3.28.0, vou adicionar a dependência no meu service users. 

Posso ir no service users e dentro dele criar uma classe que vai se chamar na br.com.alura.ecommerce, um create user service, um serviço que cria usuários.

Então ele é um cliente user service um serviço que cria usuários, então ele é CreateUserService assim como fraude detector service, ele escuta a mensagem de nova ordem de compra. 

A classe do nosso serviço é o CreateUserService, aqui é o consumer group. 

Ele vai consumir uma order.

Só vou criar isso no meu banco, processing new order, checking for fraud new user, verificando se é um novo usuário; eu vou imprimir aqui o valor e mais nada.

tenho a order e agora faz alguma coisa com essa order. 

temos o nosso código do CreateUserService, só preciso acessar o banco e fazer alguma coisa.

Agora é a hora em que usamos na nossa biblioteca o acesso ao banco, eu quero criar agora vocês é um serviço que utiliza banco de dados, utiliza um serviço externo – poderia ser enviar e-mail, enviar push notifications, salvar arquivo em disco, seja lá o que for faz algum serviço externo. 

No meu caso banco de dados externo.

Vou que eu fazer vou criar uma conexão com o banco, vou assumir que esse serviço apenas roda uma única vez e vai implementar dessa maneira Tudo bem então de uma maneira. 

Os outros serviços dá para rodar quantas vezes quiser. 

No meu caso com banco de dados se você tivesse rodando outras linguagens remoto, poderia rodar quantas vezes quiser, deixar vários paralelos.

No meu caso eu vou rodar uma sqlite que vai salvar um arquivo em disco que eu só vou ter uma instância rodando, no nosso CreateUserService construtor eu vou querer abrir a conexão com ele, então String url – uma url de conexão com o banco, jdbc:sqlite:users_database.db (ele cria esse arquivo users_database.db).

Vou criar a conexão this.conection =DriverManager.getConnection(url). Você poderia usar ferramenta de outra linguagem, tudo bem, grava no banco. 

Estou usando jdbc porque quero ir direto ao ponto.

O foco não é o banco de dados avançado, nossa questão é serviço do consumidor e serviço que acessa serviços externos e etc. 

GetConnection, maravilha, tudo certinho, pode jogar exception.

Quero criar tabela, para isso connection.creatStatement de uma maneira mais simples, como não vai ter concatenação de string e executo-o. 

Tenho no statement create table Users que vai ter o primeiro campo uuid varchar no campo de texto, vou colocar até 200 caracteres, poderia colocar fixo etc. que é uma chave primária, primary key.

Tenho também o campo que é o e-mail da pessoa vou verificar se o e-mail já existe. 

Que também vai ser um varchar e eu também vou forçar com 200, esse meu create que eu gostaria de executar; 

tem de tomar **cuidado porque esse create se a tabela já existe eu quero ignorar, a primeira ele cria a segunda tem de tomar cuidado**.

Vou deixar dessa maneira primeiro, a segunda vez que rodar vemos o problema acontecer. 

Vou jogar a exception que tem aqui, add exception to method signature. 

Se eu rodar esse serviço ele deveria ficar escutando e criar essa tabela.

Essas duas coisas ele deveria fazer, vamos rodar, quando roda o CreateUserService ele está rodando no diretório projeto-atual/ecommerce. Se eu der Synchronize ecommerce vamos ver o arquivo users_database.db.

Ele criou, caso contrário teria dado exception, teria parado, no lugar de criar nesse diretório, vou criar dentro do diretório target, para ficar melhor. Vou dar delete em users_database.db e vou rodar de novo.

Vou rodar novamente, vou dar o Synchronize ecommerce, sincronizou os diretórios, se olharmos o target veremos o users_database.db, criou e criou a nossa tabela porque não deu erro. 

Quando recebo uma nova mensagem, nova compra, novo order, eu quero verificar se já existe esse usuário, com esse e-mail.

Então, if(exists(order.getEmail) se já existe não faço nada, mas eu quero fazer se for um ususário novo, if(isNewUser(oreder.getEmail)), se for um usuário novo com esse e-mail aconteceu algo. 

Vai devolver uma string. Por enquanto não tem o e-mail.

Vou ver qualquer coisa, vai ter de receber e-mail o processo de compra, é um novo usuário vou criar nova função, por padrão vou retornar que é novo usuário. 

Se é um novo usuário quero inserir, então, insertNewUser(order.getEmail), quero implementar as duas funções, a primeira fazer um insert então, connection,prepareStatement, o statement que quero preparar é insert into users.

Campos do user: uuid, email, são os dois campos do usuário que temos. 

Faltaram os valores, values (?,?), esse é o meu statement, que eu preparei; tenho que jogar uma exception, porque pode dar erro, isso devolve para mim um statement de insert.

Prefiro chamar de insert, seta um string, meu uuid e o segundo insert.setString email, falo insert executa para mim, executou usuário adicionado, sout, Ususário uuid e + email adicionado. 

Operação simples eu prefiro ir por esse caminho.

Aqui e-mail. 

Inseri esse código, faltou uuid, vou jogar vou jogar exception e passa a ter um erro em cima, porque o KafkaService recebe ConsumerFunction - que não joga sqlException.

Na prática é raro colocar throws exception, apenas nos momentos em que quer tratar qualquer tipo de exception em qualquer linguagem; esse é o momento em que eu quero. 

Eu quero que quando eu recebo uma mensagem no meu KafkaService independente da mensagem quero ser capaz de recuperar e ir para outra mensagem.

Então, only catches Exception because no matter which Exception I want to recover and pase the next one, quero pegar a próxima. 

O problema que isNewUser, faltou comentar, verificar se é um usuário novo, aqui também quer pegar conexão connection.prepareStatement que é select vou buscar ID que é from Users where o email = ?, estou interessado em trazer um. Limite um.

Esse é o meu correto. 

É o meu query se existe, exists.setString a primeira é o e-mail que estou procurando, exists.execiteQuery, tenho que jogar exception do tipo SQL e devolve resultados.

Existe se tem próxima linha. 

Então, results.next se vai para próxima linha é porque existe, não é um usuário novo no banco. 

Verifico se é novo e insiro o usuário no banco. 

Isso quer dizer então que quando tem uma nova compra, envio a mensagem com a compra, a pessoa preencheu o site o aplicativo, os dados, o e-mail, dados da compra e enviou, ela gera uuid?

Nem sempre. 

Ela tem identificador único dela que é o e-mail, mas uuid não acontece na hora da compra, na hora de preencher o formulário, você pode fazer isso em algumas situações; mas no nosso caso, quando faz uma compra não tem uuid apenas tem o e-mail.

Então agora eu tenho um problema que tem que mudar todo o esquema de comunicação de um lado para o outro, porque na verdade a order do New Order, ela tem 10 userId, orderId e amount, não! 

Ela tem email, orderId e amount.

Isso vai para o nosso CreateUserService, quando o create cria o usuário no banco ou busca o usuário do banco aí se sabe ID dele – se existe ou não existe. 

Só vai existir uuid do usuário depois do CreateUserService se usuário é um usuário novo.

Esse é um cuidado que eu tenho que tomar, só faz sentido rodar o sistema de fraude e todas as outras coisas depois de ter colocado um usuário no meu banco, é uma decisão que a gente tem que tomar, faz sentido rodar depois ou antes com as informações na mensagem o que faz sentido? 

Isso é uma decisão que você tem que tomar.

De acordo com a decisão que toma no nosso sistema, as mensagens estarão fazendo um caminho ou outro e é isso que faremos daqui a pouco; adaptar aos nossos esquemas para isso, mas por enquanto já tem aqui um serviço capaz de armazenar dados e buscar dados de um banco e poderia usar qualquer biblioteca de banco.

### O que aprendemos?
* Como fazer um consumidor também produzir
* Como lidar com patterns e novos topics
* Como acessar um banco de dados
* Problemas de schema que vão sendo levantados durante a evolução dos serviços

## evoluindo um serviço
### Evoluindo serviços e schemas
Vimos que no momento que criou um serviço novo que se encaixa no meio do nosso processo, surgiu um problema: os esquemas e o que cada serviço está esperando receber e enviar; isso é super natural quando está trabalhando com mensagem e evolução dos nossos projetos. 

Um serviço novo aparece ou evolui um projeto e tem de pensar será que ele se encaixa como receptor - recebe.

Ou tem que se encaixar no meio entre dois passos; uma maneira de ver nosso problema nessa situação específica é quando recebo um pedido de compra eu devo primeiro colocar os dados do usuário no banco para depois detectar fraude; ou eu posso colocar os dados do usuário no banco e ao mesmo tempo ir analisando se é fraude.

Se pensar que a mensagem tem todas as informações para detectar fraude, faz os dois em paralelo, nós precisamos na order original que é no NewOrderMain passar a receber o e-mail - a pessoa preenche o e-mail lá na tela inicial dela.

Pensando como otimização dos processos e execução em paralelo, persiste. 

O problema é você recebe um pedido de compra, armazena os dados do usuário no banco enquanto está processando a fraude, pode acontecer que o usuário no banco ainda não foi salvo e está processando a fraude.

Por algum motivo armazenar os usuários no banco ficou lerdo ou caiu ou algo do gênero. 

E a fraude foi processada e enviou um e-mail para o usuário e o usuário tenta fazer alguma coisa no nosso sistema que depende dos dados do usuário estar no banco; e acontece que os dados do usuário ainda não estão no banco.

Porque processou a fraude enquanto os usuários iam ser gravados no banco, mas já enviou um e-mail falando que deu fraude, o que não deu fraude, ou seja o que for, já fez esse processo. 

Veja como é delicado, tem de pesar se outros caminhos das nossas mensagens podem fazer com que o usuário tente fazer algo que não poderia fazer ainda.

E pode; você tem de estar preparado em todos os nossos serviços, não só na compra na compra de um pedido, na criação de usuário, na detecção de fraude, no login do usuário, em todos os lugares para que um processo que pensou que já foi executado em paralelo foi ou não foi executado.

Talvez ainda não tenha sido executado. 

Por exemplo, cadastrei um produto para vender no site Marketplace, tipo MercadoLivre, Americanas, Amazon, eu posso vender, qualquer pessoa pode vender qualquer coisa basicamente. 

E a pessoa entra lá e cadastra o produto, o sistema de busca –search engine, não é atualizado na hora, em paralelo, sistema externo.

Armazenar um produto é uma coisa atualizar seu sistema de busca outra coisa; se eu fizer uma busca logo depois talvez eu não encontre meu produto, talvez eu precise esperar.

Repara que como esse paralelismo acontece, você tem que estar com os sistemas preparados para que o nosso usuário não se surpreenda caso uma informação não esteja lá ainda. E ela não está lá ainda, porque um dos serviços ainda não foi executado.

Então como vai esse caminho mais delicado mas que paraleliza mais eu queria só finalizar essa implementação. 

Primeira coisa que eu quero é poder colocar aqui quando cria a order tem também um e-mail.

Posso colocar por aqui, uma string e-mail, vou adicionar como último argumento Create Field, vou colocar como final, passamos a ter e-mail agora. 

Você poderia gerar com geradores, ferramenta geradoras ou algo do gênero, o que você quiser. 

Eu vou fazer de uma maneira bem malandrinha que é eu vou colocar aqui o e-mail da pessoa é alguma coisa aleatória @email.com.

Esse aleatório pode ser qualquer coisa, math.random está valendo, e como pode ter ponto e esse tem um ponto esse e-mail está ótimo. 

A partir de agora as minhas orders tem e-mail. 

Meu NewOrderMain já é capaz de enviar orders com e-mail, então tem LogService, FraudDetectorService, posso limpar os dois, estão rodando, vou rodar agora e gerar 10 mensagens com e-mails. 

O LogService primeiro, roda o CreateUserService gerei e estava funcionando. 

Inclusive a order tem amount e o e-mail.

O FraudDetectorService está recebendo a order com os campos e o e-mail está sendo ignorado, porque nosso Json desse analyser por padrão está ignorando campos que não existem no nosso modelo. Então você conseguiu literalmente, sem ter de fazer nada ou de evoluir um serviço, que tem agora o serviço NewOrder, tem agora e-mail e o outro serviço que não precisa de e-mail - o fraude service que por enquanto não precisa de e-mail está certo.

Evoluímos de forma independente porque o esquema não está se preocupando com isso, tem suas vantagens e tem suas desvantagens, há livros na literatura da área sobre evolução de esquema, versionamento e várias outras coisas.

Ficou tudo compatível, o que falta para terminar mesmo é o CreateUserService, que vai receber a order, agora order também precisa de um e-mail, porque o e-mail será usado. 

Vamos criar aqui o e-mail e ele vai querer usar de verdade, return email.

Tanto e-mail quanto uuid, user ID, então getUserId está no nosso CreateUserService, agora tenta inserir e para isso precisa uuid da pessoa, vou precisar também do e-mail.

Na hora de verificar isso verifique pelo e-mail , então order,getUserId, e order.getEmail, quem está gerando user ID é quem cria compra. 

Vamos rodar o CreateUserService, e dá um erro, lembra que eu tinha falado, o banco já existe, a tabela já existe, tem que colocar em cima um try catch, catch(SQLException) apenas toma muito cuidado que a exception poderia ter sido escrita errada.

Be careful, the SQL could be wrong, be really careful. Não estou tomando esse cuidado, agora terei de tomar cuidado com qual exception ocorreu.

Vai rodar de novo, banco já existe não tem problema, posso mandar as mensagens, eu vou mandar a mensagem ele deveria criar 10 usuários, encontrei um registro, mas está adicionando.

E o que pode fazer para testar se está funcionando de verdade? 

Vamos fingir que essas 10 compras toda vez que gera são do mesmo usuário. Quando eu rodar 10 compras novas como são todas do mesmo e-mail, independente do uuid, todas elas vão ser do mesmo e-mail e vamos adicionar um único usuário.

Vou rodar, gerei as 20 e dá uma olhadinha no CreateUserService, tem várias que ele não está criando porque ele criou somente na primeira. 

Ficou faltando um probleminha aqui do modelo que vamos atacar daqui a pouco.

### Escolhendo o id adequado
Continuando, vamos dar uma olhadinha para o nosso processo inteiro. 

Tem NewOrderMain que gera ID de compra, ID e um e-mail do usuário. 

Mas se tem uma pessoa fazendo uma compra e eu ainda não sei se eu tenho esse usuário no banco ou não, eu tenho ID do usuário ainda?

Na verdade até o identificador é o e-mail não é userID, repara que nesse instante percebe que o desenho do sistema não está fazendo sentido, porque quando tem um processo de compra a pessoa digita o e-mail dela e ela tem o número de compra, o número do pedido da compra.

Primeiro o identificador do usuário é o e-mail do usuário, certo então já tem identificador do usuário, mesmo que queira gerar um identificador único atrás de um uuid para o usuário; esse não é o momento, não é a cada compra do usuário eu gero novo uuid do usuário para ele, não.

A cada compra do usuário eu gero ID de compra do usuário, apenas se ele é novo daí gero ID. 

Esse user ID que estou criando não deveria existir, a nossa order não tem userID, tem order ID, e tem o e-mail; se você quiser pegar informações do usuário que fez essa compra, vai ter de acessar um banco ou um serviço. 

Que a partir do orderID você navega nos dados do usuário que fez essa order.

Quando faz a compra não tem orderID, não tem userID. 

Agora faz sentido com um site que você costuma comprar. 

Tem um problema, porque quando está enviando uma order, enviando o pedido de compra, qual que é a chave que eu vou usar?

Estávamos usando ID do usuário, garantia que se um usuário faz duas compras esse usuário vai ser primeiro processada a primeira e depois a segunda, vai ser sempre na ordem; para mesma chave vai ser executado sequencialmente.

Se o usuário faz três compras, vou tentar a primeira dele, depois a segunda, depois a terceira, nessa ordem que eu vou tentar. 

Se usar orderID vai rolar? 

Pode ser uma chave válida, o problema é toda compra orderID é uma compra de valor novo, então dentro de um usuário se ele fizer três pedidos pode ser que o segundo seja antes do primeiro, que vem antes do terceiro.

Eu quero garantir que todas as compras do mesmo usuário vem em ordem, sejam processadas em ordem através do ecommerce new order da mensagem e depois para frente vai ser a chave que vai ser enviada. 

Em vez de usar orderID eu uso o e-mail como chave do meu usuário, então tanto NewOrder quanto sendmail vai ser usado nessa ordem.

Eu tenho e-mail sendo usado como chave, NewOrderMain joinha. 

Tenho de observar os outros serviços, que recebe. 

Primeiro o CreateUserService recebe o ECOMMERCE NEW ORDER, agora a nossa classe order não tem mais, então eu apago aqui, não tenho mais CreateUserService.

Quando estou criando usuário, se o usuário é novo, insere o usuário. 

Se eu vou inserir usuário eu ainda não tenho ID dele, eu vou gerar uuid, então UUID.randomUUID, toString, nesse caso gero ID novo para esse usuário, só nessa situação.

Esse CreateUserService que estava lá. 

Service-new-order já passei, service-log trabalha com Strings, service-fraud-detector também trabalha com order, vamos lá. 

Não tem mais userId. 

Vou salvar e vou fechar. você fala o código está funcionando, quase. 

porque quando dispara mensagem para próxima fase - no meu caso tem uma próxima fase nessas duas situações, eu estava usando userId para dizer já que a compra foi rejeitada ou aprovada, dentro das compras aprovadas a próxima fase eu quero enfileirar as mensagens de acordo com o usuário.

Sabe não tem userId na compra, agora que pelo detector de fraude foi aprovado, precisa processar o pagamento. 

Pensa que vai processar o pagamento, provavelmente vai querer fazer na ordem que o usuário teve as suas compras aprovadas.

Agora pelo detector de fraude, se você falar não faço questão que seja na ordem que for aí você poderia usar outra chave. 

Como estou usando por usuário eu vou pegar o get.Email. 

O rejeitar, eu quero enviar as mensagens de rejeição ou fazer o processamento de rejeição no usuário de acordo com a ordem, talvez sim, talvez não.

Você acaba discutindo, por padrão a chave vai ser por usuário, assim sabe que as tarefas de um usuário dentro de um tópico vão ser executadas em sequência, você poderia querer executar em paralelo, tem situações que sim e que não. No meu caso aqui por padrão get.Email.

Mas get.Email não existe, porque nossa order do FraudDetector não estava usando e-mail, basta receber, não precisava estar no construtor, precisava estar com uma variável membro aqui para Json utilizar.

Aqui colocar o get.Email, se você quiser apagar e gerar novamente toString que ele vai colocar o e-mail no toString, toma cuidado para ter uma senha sendo impressa. 

Temos o nosso FraudDetectorService também utilizando o e-mail.

Você pode falar por padrão todas minhas mensagens tem de ter usuário e eu quero que seja realmente sequencial dentro de um usuário. 

Você poderia fazer métodos send de programação que fosse o T, tem de ter um método do tipo get e userId ou outro.

Por padrão seria isso. 

Você deixaria uma outra opcional, porque que você quiser fazer um processo de get para usuário - quero fazer o extrato do ano passado, eu quero processar várias coisas ao mesmo tempo, de vários anos; quero processa-los em paralelo. 

Chama o send com chaves distintas.

No nosso caso quero sempre está dizendo a chave, sempre forçando desenvolvedor a escolher paralelo ou quero sequencial. 

Vou rodar o CreateUserService, vou rodar também o fraud detector service e vou rodar o log service e enquanto ele está rodando eu vou dar o nosso e-mail, e-mail service.

Então eu vou ter aqui 4 arquivos rodando, quatro classes de serviço rodando e quando as quatro estiverem de pé eu vou dar o meu NewOrderMain para que envie as mensagens de 10 compras de um mesmo usuário e veja como vai ser esse processamento.

E agora eu sou capaz de rodar 10 mensagens, vamos rodas as mensagens. 

O Create rodando, ele vai inserir na primeira vez por causa do e-mail.

Já o nosso FraudDetector está rodando as várias vezes, tem orderId, amount, email, userId não faz sentido, é só banco de dados por enquanto. 

Claro se algum serviço precisar é só pedir, seja acessando o banco direto - que seria meio estranho, você com a mão no banco de dados de outro serviço - ou comunicando via https mensagem. 

Conseguimos isolar o que fazia sentido em relação ao ID.

### O que aprendemos?
* como evoluir um serviço sem quebrar os schemas
* como pensar a evolução de um serviço
* discutindo UUID e id único 

## servidor HTTP
### Usando um servidor http como ponto de entrada
Por enquanto você fez pedido de compra através de um método Main, um programa que você roda. 

Pode ser que você tem um programa que você roda uma vez por semana, uma vez por dia, quando você quiser e ele gera mensagem se o sistema fica rodando.

Assim como os nossos serviços, esses quatro serviços, você criou um que se comunica com serviço externo é comum também o ponto de entrada das mensagens seja uma camada que tem comunicação com o sistema externo, o ser humano através da internet.

É muito comum que a atividade que seja feita que dispara uma primeira mensagem, dezenas e centenas de mensagens para portais é a interação humana com o site, por exemplo, pedido de compra de uma página Web que acessa. 

Aperta o botão envia alguma informação e isso dispara uma primeira mensagem.

Vamos tratar a outra ponta, onde o serviço começa através da web e vai ver interação de um serviço web com o Kafka. 

Vou criar um novo serviço, o meu serviço vai ser o serviço http agora, um novo módulo, porque o site http vai estar no meu caso inteiro em um serviço – poderia ter vários sites.

Eu vou ter um service http, você poderia ter vários serviços http, ecommerce, vou criá-lo. 

#### Jetty como servidor http
Agora eu quero criar aqui um servidor http; para fazer isso vai usar uma biblioteca que já existe um servidor http que já existe no mvnrepository jetty que tem diversas versões e as mais recentes são do org.eclipse.jetty server que vai dar o core.

Quero o jetty-servlet, que dá apiServLet de Java, use a que você quiser no seu dia a dia de acordo com sua biblioteca, ferramenta, com a sua linguagem etc. 

Eu estou pegando uma que é muito simples de usar, com poucas linhas de código vai conseguir trabalhar.

Não me importa se api é nova, velha, antiga, importa que é simples e pode colocar rapidamente no que se quer e eu vou adicionar as dependências, dependencies, formato e salvo, para que comece a baixar nossa dependência.

Vai baixar o jetty-servket e baixa o jetty core, na hora que terminar de baixar vai poder criar no service http ecommerce, pode criar uma nova classe br.com.alura.ecommerce, vou criar o meu HttpEcommerceService, dê o nome que faz sentido.

Vou ter um Main que vai rodar um servidor, com o Jetty, primeiro vou criar um server que é new Server. 

Como eu quero abrir a porta 8080, por padrão, eu vou fazer o meu servidor rodando nessa porta. 

Sever.start, estou rodando.

Não quero simplesmente rodar, quero fazer mais coisas, primeiro eu quero esperar o servidor terminar para eu terminar minha aplicação, server.join faz isso, fica esperando. 

Ele pode jogar uma exception interrupted, pode ser que alguém mandou parar.

E dá um interrupted exception, antes de começar eu quero configurar, para o servidor quando alguém chamar uma requisição, eu quero que você lide server.setHandler essa requisição através de um contexto que eu vou criar, context.

Vou criar um contexto que lida com as requisições, no meu caso vai ser um ServletContextHandler eu posso até passar parâmetro se eu quiser, vou deixar padrão. 

Vou em context, seta que o padrão que eu vou querer ter é /, é nada, é raiz mesmo, 8080.

Quando alguém chamar localhost:8080/ eu vou adicionar uma Sertlet, a minha new ServletHolder do Jetty e aí eu passo pra valer, que faz NewOrderServlet eu vou falar que ela vai na uri/new, quando acessar vai na servlet. 

Essa a aqui a configuração, se quiser adicionar mais servlets, só adicionar, divirta-se.

Escreva o código que você precisa para ter um servidor http, dentro desse servidor eu tenho vários métodos que eu posso implementar. 

Se eu estender HttpServlet vai mais rápido, eu importo, aí tudo fica opcional. No nosso caso eu vou fazer da maneira mais simples que é o método do doGet.

É o get do http, quando acesso uma uri seca e não vou chamar o super e faço o que eu quiser. 

No meu caso eu quero criar um pedido, eu vou pegar tudo isso e jogar lá dentro; mas eu vou gerar os dispatchers. 

está reclamando do order, a classe order não está aqui.

Precisa da classe order, vou pegar do NewOrder essa classe porque é como a estou usando, copiar e colar. 

KafkaDispatcher, preciso adicionar dependência e importar, código inteiro compilando, falta pouquinho. 

Criamos KafkaDispatcher, criou dados. 

Eu não quero enviar 10 pedidos de compra, se acessou via web é um pedido de compras só.

Ele está reclamando do método send, que pode dar uma Exception e eu tenho que tomar cuidado, eu tenho que colocar isso no try catch.

Qualquer uma dessas duas exceptions, vou deixar vazar, vou jogar servletException com esse e. 

Vamos rodar, esse código o Http ecommerce service, no nosso run tem vários programas rodando, tem uns quatro serviço, eu vou limpar para ficar bonitinho. 

Vou ver local host 8080/new. 

Não mostrou nada e enviou as duas mensagens, fez todo o processo das mensagens.

Está faltando refinar um pouco o código. 

Agora que tem um servidor rodando, refina, eu tenho uma mensagem processei o que tinha de processar. 

Depois disso, eu vou dar um sys out, para que no servidor veja que processo da nova compra terminado, mas como estamos escrevendo em inglês, eu vou falar New order sent successfully.

Então eu tenho que ela está sendo enviada e eu quero mostrar uma resposta aqui, cada um vai ter sua maneira, a minha api, é resposta resp.getWriter, println e imprime o que quiser.

Vou mandar a mesma coisa, New order sent, é a mensagem que eu vou enviar, eu poderia devolver 200, para devolver um 200 resposta setStatus eu coloco direto 200, posso restartar esse New order, HttpEcommerceService e acessar de novo.

Quase lá, porque toda vez que acessa, estou enviando dados aleatórios, eu não queria, eu queria de maneira simples. 

O nosso foco é mostrar que o ponto de entrada http também consegue lidar com o resto. 

Vou passar um parâmetro aqui.

A compra tem como parâmetro o e-mail, Guilherme@e-mail.com, tem o valor, 153 e enviar, quando chamar aqui, quero ler esses valores e eles estão no request req.getParameter email, não é mais aleatório.

Depois tenho amount no BigDecimal não é aleatório, req.GetParameter amount, não é uma questão de segurança, we are not caring about any security issues, apenas how to use http as a starting point.

OrderId toda vez que gera compra nova está gerando a compra nova, posso gerar de maneira o orderId, tenho os valores, crio a order e envio. 

Vamos testar, reestartar.

Será que enviou? 

Criou o e-mail. 

No Fraud Detector, 153, funcionou, por fim repara que toda vez cria um orderDispatcher e emailDispatcher, talvez você queira fazer isso através de injeção de dependências, inversão de controle, o que você quiser. 

No caso de servlet tem uma maneira muito simples de fazer. 

Se você usar injeção de dependência, inversão de controle, o que for, os utilize de componente.

No meu caso eu vou ter aqui um método que se chama init que recebe o servidor de config e nesse método vou poder inicializar. 

O educado é inicializar neste instante só se você fizer nesse instante, vai precisar de variável que não vai ser final, variável membro que não é final.

Lembra, tudo que não é final, que é mutável, pode ser nulo e acarretar em vários possíveis problemas; 

eu vou evitar isso eu vou diretamente colocar aqui private final e vou criar meu KafkaDispatcher, na construção da Servlet.

Esse meu order e a mesma coisa no meu e-mail Dispatcher, um KafkaDispatcher de String, private final de String. 

Tenho meu dois Dispatchers. 

Não preciso mais desses dois trys, apago.

Posso formatar, agora tem um código que inicialize uma única vez, quero destruir, apago e dou destroy, super.destroy, no destroy vou fazer orderDispatcher.close emailDispatcher, deixo rodando, faz a injeção de dependência, a inversão de controle, super simples de fazer isso.

Poderia colocar umas edificações a mais, mas o básico super simples para ir direto ao ponto, envia o meu e-mail e se usuário já existe agora o Guilherme e-mail, ele não cria novamente; o FraudDetector passa 153 etc.

Mas se eu passar um valor aqui muito alto, tipo 5100, agora 5100 vai ser considerado fraude, lembra. 

Deu fraude, está tudo funcionando como nós queríamos. 

Pode ter um starting point - um ponto de entrada ou algo do gênero, que trabalha com as mensagens para dentro, não precisa ser um método Main de command line, pode ser http, pode ser o que for e você trata com os componentes, cada um desses componentes da maneira que tiver de tratar.

### Fast delegate
Nesse momento você está se perguntando “quanto de código eu coloco no meu servidor http?”. 

Porque pode ser que eu tenho as coisas que eu queria fazer e não demoram tanto, por exemplo, enviar um e-mail será que não dava para enviar aqui dentro do meu servidor http?

Você poderia colocar a biblioteca de envio de e-mail aqui dentro, usa Google, ou seja, quem for para enviar o e-mail, qual o problema? 

Quanto mais código você colocar aqui dentro maior a chance de crashear e você perder a compra, perder a requisição do usuário final.

Quanto mais código eu coloco aqui, maior a chance de dar uma exception, maior a chance de dar um erro e de algo não funcionar e ninguém saber o que aconteceu. 

Enquanto eu não envio a mensagem, eu não tenho uma maneira fácil de replicar todo o processo.

Se eu envio a mensagem e todo o processo de compra começa com envio de uma mensagem, se eu tenho essa mensagem logada em algum lugar e ela falhou e quiser tentar de novo, eu tenho enviar a mensagem de novo.

É muito fácil de tentar de novo, você tem um monte de código aqui, como para tentar de novo boa sorte, você tem que acessar essa página de novo com o usuário logado, com um monte de coisa marcada para você poder enviar essas mensagens.

Quanto menos código em toda a ponta de entrada, mais rápido você delega para o sistema de mensagens, quanto mais rápido você enviar essa mensagem menos chance de dar erro aqui.

Se tiver, mais fácil de replicar, esse fast delegate joinha. 

Porque fala para o usuário sua compra está sendo processada, boa sorte, você pode fazer um refresh e um web socket para descobrir quando foi processada, outras abordagens depois para saber quando a compra terminou.

O mobile enviou uma mensagem de quero fazer uma compra, estou fazendo a compra. 

Parabéns sua compra está sendo efetuada, daqui a pouco se comunica ativando um push notification. 

Atualiza a tela, seja lá o que for, mas o mais rápido possível.

Se o servidor o mais rápido possível dispara uma mensagem, para não ter chance de erro. Ele “se vira com o resto”. Essa a dica de um ponto de entrada, mínimo de código possível, mínimo de processamento, deixa que uma mensagem faça tudo para você.

Mesmo aqui que eu estou enviando duas mensagens - meio ruinzinho. 

Poderia argumentar de ter um serviço separado ou no mesmo serviço, que é realmente um novo pedido de compra e ele recebe do NewOrderMain uma mensagem e ele dispara as duas mensagens.

Como não pretendo tocar mais no NewOrderMain só pretendo usar esse que dá para passar parâmetro, só pretendo tocar no Main quando eu quiser rodar várias vezes. 

Assim, não vou me preocupar com esse caso, NewOrderMain, ficou mais um teste nosso do que o para valer, o nosso ponto de entrada para valer é NewOrderServlet.

### O que aprendemos?
* como usar um servidor http embarcado
* como criar um serviço http
* como enviar mensagens a partir do servidor http
* a vantagem de um fast delegate

## cluster de brokers
### Single point of failure do broker
Chegamos em ponto bem interessante, temos diversos serviços que somos capazes de rodar e mantermos rodando. 

Então quer dizer que se eu quiser deixar rodando dois HttpEcommerceService numa máquina e outro em outra máquina é possível.

Um em uma porta e outro em outra porta, também. 

Na verdade o jetty já é multi trajeto, já é capaz de receber várias requisições simultâneas. 

Se eu quiser rodar dois CreateUserServices, maravilha. 

Se eu quiser rodar 10 FraudDetectorServices, maravilha. 

O CreateUserServices como estou usando um banco em disco através de uma maneira específica sqlite eu só vou se for dar um por vez.

Mas se você tivesse acessando um outro banco também, se eu tiver acessando o FraudDetectorService que é 15 rodando, também. 

LogService três, maravilha, e-mail service 18, também.

Cada um desses consumer groups vai consumir em paralelo, no máximo o número de partições que existem naquele tópico, se NewOrder tem três partições eu posso tomar três FraudDetectorService no mesmo consumer grupo e cada um vai pegar um; o quarto vai ficar parado sem fazer nada.

Tem três pelo menos, poderia deixar um para caso algum caia rapidamente. 

Mas também você iria querer detectar que um caiu para restartar, fazer coisas do gênero na parte de operações. Três para três certo.

Você vai querer ter as partições e número de consumers igual das partições, por exemplo, para paralelizar ao máximo. 

O status do servidor é visto no terminal, tinha vários comandos que ajudavam, um dos comandos era para verificar a situação dos tópicos.

Era bin/Kafka-topics.sh e eu pedia para descrever no nosso bootstrap-server que é o nosso localhost:9092, ele falou de vários tópicos, o tópico ECOMMERCE_ORDER_APPROVED tem três partições, a zero, um e dois. O ECOMMERCE_SEND_EMAIL tem três partições, ECOMMERCE_ORDER_REJECTED também.

O Kafka está pingando e pedindo essas informações, consegue perguntar dessa maneira, tem o comando para entender do consumer groups, mas estou interessado nos tópicos.

Agora eu quero dar uma olhadinha nos tópicos o que acontece se eu derrubar o meu Kafka? 

Vou derrubá-lo. 

Vou limpar tudo, deixei rodando. 

Inclusive o HttpEcommerceService, mas ele está falando que não conseguiu conexão na porta 9092.

O broker deve estar indisponível. 

Por mais que os services possam ter mais de uma instância de entrada e de saída, o ponto de falha não é único, o broker é único.

E se eu tentar enviar uma mensagem agora. 

Tentei, o nosso send foi implementado com a função chamada do get que espera sucesso ou fracasso, está esperando e travou. 

A abordagem não está correta; 

depois veremos muitos tratamentos de erro, mas quero discutir o ponto de falha, broker.

Vou levantar o Kafka broker, o HttpsEcommerceService percebeu, despachou para os tópicos e o CreateUserService percebeu que ele levantou fez de novo rebalanceamento das partições e falou as três partições são para mim, porque só tenho eu no meu consumer group.

Pegou, recebi uma mensagem, processou, o FraudDetectorService que já estava rodando, mesma coisa, percebeu que o broker levantou e fez. 

Todos eles “se viraram” com a queda e o levantamento no nosso broker de novo.

Mas ainda ficou com a falha, se eu tenho 2 log Services rodando, pode pegar o LogService e mandar rodar, vou no Edit Configurations, LogService, copia e o criou outro (1).

Dou ok e vou querer rodar o LogService 1, agora quando logar o LogService 1 ele estará com algumas partições e o outro com outras partições, pegou quatro partições. Agora rodo de novo uma mensagem.

Disparo e aparece nos nossos LogsServices, esse recebeu ECOMMERCE_ORDER_REJECTED, o ECOMMERCE_SEND_EMAIL e o ECOMMERCE_NEW_ORDER. E o outro não recebeu nada, vou rodar novamente com outro e-mail.

E-mail é a chave das nossas mensagens. Agora veio nesse LogService, maravilha. Está funcionando, se um Log Service cai, se eu derrubar esse LogService outro LogService vai assumir, daqui a pouquinho ele vai assumir as partições novas.

Ele tem que assumir as partições novas porque alguém vai lidar com as partições que caíram, peguei aqui todas as partições.

Então agora se eu chamar para esse e-mail, para esse e-mail ou para outro, todos vão cair no nosso LogService. 

Como eu tenho dois serviços desse rodando, não tem single point of failure, se um cai o outro assume. 

No broker não. 

Quando um broker cai só tem um. 

Vamos rodar diversos brokers daqui a pouquinho.

### Replicação em cluster
O que eu quero fazer agora é levantar dois brokers, porque se um cair o outro está de pé; 

agora vou deixar tudo rodando, estou com meu sistema rodando. 

O que eu vou fazer é abrir uma nova janela, eu vou agora configurar um segundo servidor.

Há aqui no config um server.properties para config/server2.properties, você poderia fazer isso programaticamente de diversas maneiras para automatizar a configuração, toda a parte de devops para operação, vou fazer manual para ter controle fino sobre as coisas nesse instante.

O server dois eu vou editar, pode editar com qualquer editor, as configurações tem que mudar, primeiro esse tal de broker.id que é zero do nosso primeiro broker. 

E ele fala que cada broker tem de ter um número inteiro único.

Se o server 2 ponto properties eu vou colocar dois. 

Não precisa ser um, vou colocar dois, para que fique fácil de identificar que o dois é o server 2 e assim por diante.

O broker ID dois, tem o zero que é o primeiro que não tem número e dois que é o server dois. 

Vamos descer, tem o diretório de logs Users/alura/Documents/guilhermesilveira/1152-kafka1, não pode usar o mesmo diretório, são brokers diferentes, aqui coloco kafka2.

Porque eu sei que é o server properties 2. 

Vou procurar a palavra port, por padrão é 9092 e quero colocar 9093, e tem um problema 9092 seria do número 2. Se quiser manter um padrãozinho até 9. Mas se você quiser manter o padrão não precisa, até porque estou rodando esses dois blocos na minha mesma máquina.

Se minha máquina cai já era, talvez na internet as suas máquinas você vai querer rodar um broker em cada máquina, todos eles na porta 9092, cada uma diferente. Esse que é o nosso server dois eu vou rodar na 9093, salvei, fechei e agora bin/kafka/server-start.sh config/server2.properties.

Vou levantar, o server dois falou que levantou, se ele levantou estou feliz, KafkaServer id=2; o Kafka está levantado e zookeeper rolando, vamos nos nossos projetos/serviços rodando.

Vou enviar nova mensagem, mensagens enviadas, foram todos tratados direitinho, no Log do Kafka 1 e 2 nada novo. 

Vamos brincar com os servidores, derruba o broker, derrubei. 

Os serviços percebem que o broker não está disponível - não está mais comunicando, estava o tempo inteiro e de repente não conseguiu.

Não conseguiu comunicar com o node zero. 

Se eu levantar de novo, recupera a conexão, pega as partições e vai para frente, está continuando o processo.

Vou parar todos os processos, eu quero ver os tópicos, descrevê-los, mandei descrever e temos o de compra new order tem três partições e dois estão rodando, porque que quando derrubou o primeiro ele não se conectou com o segundo?

Quando derrubei o primeiro esse não tentou se conectar com o segundo, ele simplesmente desistiu, se você olhar os tópicos, apesar de três partições, a partição zero, um e dois estão localizadas no broker leader que é o zero. O zero caiu ele tem que ir para uma das réplicas que é uma cópia do líder, só tem uma que é a zero.

No momento que um caiu, não adianta conectar no outro porque o outro não tem as informações. 

Não adiantaria eu mover para esse daqui porque aqui o outro não tem as informações, as informações da partição zero estão no líder zero, e mais nenhum lugar só no zero e eu queria que tivesse no 2 também.

Então queria replicar as partições em dois lugares, vamos rodar o CreateUserService, posso rodar por aqui também. 

Vou rodar fraude, e-mail e HttpsEcommerceService, temos os quatro rodando. 

Eles estão rodando, ele informa que tem 2 producers nesse cluster. 

Eu posso rodar o comando topics de novo e não mudou, porque o tópico tem que ter uma replicação maior, e continua com replicação – eu poderia pegar esse tópico e alterá-lo.

Vou alterar bin/Kafka-top serve também para fazer outras mudanças - mais partições, mudar a replicação; é o que farei agora, eu posso passar o bootstrap ou zookeeper e passa direto zookeper localhost:2181 e quero alter topic ECOMMERCE_NEW_ORDER quero três partições e replication factor 2.

```
bin/kafka-topics.sh --zookeeper localhost:2181 --alter --topic ECOMMERCE_NEW_ORDER --partitions 3 --replication-factor 2
```

Quero que ele replique em outro broker, tem o líder e mais um, fez, deu erro e vamos ver o que ele disse. 

**O alter não pode ser feito ao fator de replicação** – que eu não consigo alterar agora. 

**Quando cria o tópico tem de definir o fator de replicação.**

Então vamos adicionar nova propriedade de replicação no server basics, default. replication.factor = 2 – eu fiz isso no config server properties 1, vou fazer o mesmo no 2. 

Às vezes você vai querer fazer isso em produção com devops para distribuir para 10 máquinas do cluster, talvez fazer de maneira automática, manualmente quando tem dois, três, quatro, cinco não tem problema.

Default replication igual a dois, tenho os dois arquivos, vou ter de parar todos, como se tivesse começando do zero, vou matar o zookeper e vou apagar os diretórios data/Kafka/* o data/zookeeper/*.

Vou começar zookeper novamente, Kafka zero e vou começar o kafka2, agora que startei todos vou conferir se tem os tópicos limpos. 

Confere que zookeeper e Kafka diretórios estão limpos.

Não tem tópico nenhum, sistema novo que definiu replication factor de 2, 3 partições, vou rodar primeiro o LogService, depois vou rodar o FraudDetectorService, o EmailService e o HttpEcommerceService, todos se comunicando. 

Se eu pedir tópicos existem já. 

O tópico ECOMMERCE_NEW_ORDER tem a partição zero, um e dois porque pedimos três partições – está no líder que é zero e também com uma copia no 2.

Como a réplica é dois ele sempre vai estar no dois, todas as partições estarão nos dois. 

Se um por um acaso cair tem informação no outro legal. 

A partição diz o número da partição, o líder quem é o principal para onde é feito o write, a escrita das mensagens e o líder replica para réplica, caso líder caia a gente tem as réplicas sobrevivendo.

Vou testar, rodar, vamos enviar uma mensagem, recebi as mensagens, o CreateUserService tem de rodar, vou rodar, vou enviar novo email e recebeu o novo email. 

Todos funcionando, se mandar uma terceira mensagem teste, o que falta é ir nos nossos Kafkas, temos dois.

Vamos limpar, mata esse zero, estou derrubando, se eu dou um Kafka-topics já derrubou, o kafka fala que não consegue conectar no 9092, claro, eu dei um kafka-topics no 92, vou dando 9093. 

No 9093 ele falou o líder agora é o dois, todos os tópicos para todas as partições. 

Como ele tinha uma réplica e a réplica estava atualizada, ele virou líder de tudo.

O zookeper e o kafka decidiram que ele é líder de tudo porque não tinha mais ninguém para ajudar. 

Aqui que você vai escrever, todos os nossos “caras” agora estão conectados, mas não mas o 92 não dá mais para estar conectado no 92.

Vamos tentar enviar de novo, HttpEcommerceService enviou mais uma, tudo sendo enviado, e-mail, envia mais uma eu tenho agora mais, FraudDetector, e-mail, esse http ecommerce, LogService, sendo logado. 

Com isso vimos que é capaz de derrubar kafka e o cluster continua de pé porque tem um outro rodando.

Vou rodar de novo esse primeiro, vou tentar, mas antes dá uma olhadinha aqui nos tópicos. 

se olhar os tópicos vai ver que realmente o líder agora é o dois na maior parte das coisas, mas tem algumas coisas que o líder está none - não tem líder.

Só tinha informação na réplica zero, então para algumas coisas não deu certo, porque até qual mensagem que eu já li estava sendo armazenado somente em um lugar. 

Alguns deles não vão funcionar, CreateUserService está travado.

Eu teria de levantar de novo e ver ele se recuperar. 

O CreateUserService levantou e executou, voltou a executar, assumiu e voltou executar aqui no meio. 

Agora ele volta executar, se for lá nos tópicos tem líder em tudo de novo.

O líder ficou dois em todos eles e ele faz o backup no 0. 

**Isr** são quais são as réplicas que estão atualizadas até esse instante; as duas réplicas estão super atualizadas. 

Se eu rodar de novo mais uma compra vai ver que recebe a mensagem nova.

Está funcionando, está com ele levantado, ele virou 2 em tudo. 

Ficou com um ponto de falha que é quando derruba o consumeroffsets por estar escrito em um único lugar como kafka armazena qual foi a última mensagem que a partição leu em um tópico se esse kafka broker cair perde a informação.

### Cluster de 5 brokers e explorando líderes e réplicas

O próximo passo é cuidar de algumas outras configurações, vamos no config server properties e dar uma olhada. 

#### consumer offset replication
Quando eu procurei replication existe um o replication factor para os metadados dos Tópicos; o consumer offset.

Qual que é o valor que deve utilizar para qualquer coisa a não ser desenvolvimento? 

**Um valor maior do que um é recomendado**. 

Então por favor, coloca o valor maior do que um, porque se deixar um tem o problema de ficar dependente ainda de single point failure.

Ele recomenda 3 que é o valor que você vai encontrar em algumas empresas. 

A replicação em vez de um vou colocar 3, o offset vai estar com replication 3 e também o transaction.state.log.replication.factor vai colocar 3, mesmo o default.replication.factor vai colocar 3. Vamos passar a rodar 3.

Vou salvar isso daqui, entrar no config tem vários arquivos server, server 2, vou remover o server dois, vou parar o dois, o zero, tudo. 

Vamos criar 4, o server properties que é o padrão, toda vez que eu usar esse padrão eu vou mover esse server properties para server 1, 2, 4. 

Vou pegar um e copiar como dois, vou copiar como três e vou copiar como quatro.

Vou querer rodar quatro brokers e em cada um do dois em diante eu posso mudar alguma coisa, o broker id de dois é dois, o diretório do dois é esse diretório kafka2, mesma coisa para 1. O um a porta é 9091, o dois na 9092, o três na 9093.

Estou rodando na mesma máquina sem problema e vou configurar o diretório como kafka três, mesmo processo para o 4, porta 9094. 

Eu tenho quatro arquivos agora, parei o primeiro, segundo, parei o zookeeper.

Vou remover os diretórios. ls data, não os quero, rm – fr data/kafka/* porque ele cria tudo sozinho. 

Eu vou startar zookeeper, vou startar com properties um, com o dois, vou chamar o bin/kafka-server-start.sh 3. E vou ver abrir o 4 e temos quatro desses rodando.

Por algum motivo ele não pegou quatro, sempre no um, dois e três. 

Eu os levantei com o IntelliJ rodando, como isso vai acontecer ao mesmo tempo, startei os brokers com os programas rodando, ele criou nos brokers que estavam disponíveis. 

Vamos rodar o CreateUserService. Maravilha! 

Vou rodar o LogService, vou rodar o e-mail service, vou rodar o FraudDetectorService, HttpEcommerceService.

Estou com cinco programas rodando, você vai ver que cada um deles está com partições distintas e várias coisas diferentes. 

Podemos conferir os tópicos novamente, todos aqui: um, dois, três. Vou enviar o enviar para o e-mail novo. 

Enviei a mensagem e funcionou normal, ele está usando o nosso cluster.

Se observar os tópicos de novo tem um, dois, três. 

E se eu derrubar? 

Porque o primeiro de toda a primeira a partição é o líder 3, vou derrubar. 

Roda o tópicos e tem no 9091 todos os tópicos. 

O 3 está sendo usado ainda, réplicas 3, 1, 2.

Qual está atualizada? 

A um e a dois, a três não está atualizada. 

Afinal a 3 está caída, não está atualizada. 

Nesse tópico aqui tem legal 4. Vou fazer com zookeeper 2181, tem todas as partições e o líder 1, 2, e 3. 

Tem o 4, o três aqui está fora do ar.

Em alguns casos um está fora do ar e o outro. 

Posso levantar uma quinta máquina. 

Eu copio o config/server4.properties para config/server5.properties, se observar os dados tem kafka1, kafka2, kafka3, kafka4. Vamos no config server 5 e digitar, o broker.id cinco, porta 95 e o diretório é o diretório cinco.

Salvei e dou start, bin/kafka-server-start.sh, config/server5.properties. 

Então vou levantar uma quinta máquina. 

Mas lembra na verdade a terceira está derrubada. 

Ele levantou o cinco e vamos nos tópicos, ver no zookeeper como está, ele tem um, dois, três, quatro e o cinco não está sendo usado.

Posso enviar mensagem. 

Se for lá vai ver que a mensagem replicou. 

Será que teve réplica das mensagens? 

Pode ir para os tópicos, tem o líder 1 e por aí vai. Posso tentar derrubar um dos servidores, o dois agora.

Ele está notificando que está saindo fora do ar. 

Vou olhar os tópicos de novo, tem aqui por exemplo um que tava no 3 e no 2 e que agora está só no um. 

Esse outro aqui está no quatro, está ficando perigoso, caindo vários brokers. 

O líder está de pé. 

Porque se o líder cai e dermos shut down no líder, uma outra réplica que está bonita tenta virar líder - vários detalhes se é exata ou não no caso de falha para discutir depois.

Acontece o um derrubou, um dos que está de pé vira líder e a comunicação continua funcionando. 

Se eu rodar aqui funciona. 

As mensagens vão e as mensagens chegam, porque alguém se tornou líder. 

Repara como no primeiro tópico apenas um está de pé.

Só um está sincronizado, vou levantar o dois, quando eu levantar esse tópico aqui o líder, o líder vai mandar informações a ele, para atualizá-lo. 

Queria atualizar o 3 também, levanto e sincroniza também.

Quando rolar um balanceamento, quando criar um novo tópico ele pode passar a usar o 5 também. 

Então a gente tem esses sincronizados, não tem mais aquele ponto de falha único, agora tenho cinco rodando, inclusive se eu destruir o primeiro, ele notificou todos que está sendo destruído.

Escolheu outros líderes; se eu destruir o segundo ele está notificando todo mundo e escolhendo outros líderes; se eu destruir o terceiro ele está notificando todo mundo e escolhendo outros líderes.

Estava tudo no 1, 2, 3, 4, deveria estar no 4 as coisas. 

O terceiro ainda não derrubou está rodando, tentando derrubar, tem de eleger os lideres demora um pouco.

Os tópicos deu problema, tem caos que não tem mais líder, porque essas informações não estavam no quatro. 

Então o ponto único de falha não existe mais o que existe são três pontos de falha, se um dois ou três derrubaram e a réplica era um, dois e três já era. 

Mas não é mais um ponto único de falha.

Se cada um deles está rodando de máquinas diferentes não temos mais ponto único de falha, a replicação é feita automática, o levantamento e a ressincronizacao desses dados é feito de forma automática, estou levantando os três de novo. 

Fazendo de forma automática, a escolha dos lideres automática.

Tudo isso é distribuído e balanceado e os lideres são escolhidos na medida em que são derrubados de novo e está replicado em algum lugar. 

Levantei alguns, talvez todos e tem líderes agora distintos e as réplicas estão sincronizadas de novo.

### Acks e reliability
Vimos como é interessante o Kafka de trabalhar com replicação de forma simples, com cluster de broker de forma simples, de trabalhar com líderes e réplicas de uma forma simples. 

Simples, comparado com a complexidade toda seria fazer tudo isso na “unha”. 

Simplificado comparado com o que poderia ser feito.

Não quer dizer que é simples como tocar um “tomar um suco de maracujá”. 

Vamos tentar detectar mais um caso complicado de falha, o que acontece se eu tenho o meu código, meu sistema rodando, eu envio uma mensagem e a mensagem chega no líder, mas as réplicas estão caídas.

Elas não estão lá, elas estão fora. 

Imagina que eu envio, tenho cinco máquinas rodando meu cluster, quatro delas saíram do ar e eu envio a mensagem e a mensagem chega nessa uma única e não replicou ainda. 

Antes desse um levantar e poder replicar para cá, essa máquina cai e perde os arquivos e aí essa outra levanta - ela está com o estado antigo, ela não está com o estado novo.

Porque não deu tempo da máquina que caiu enviar as informações para réplica. 

Lembra que estamos com réplica três, independente de ter cinco máquinas. 

Outra situação é a nossa máquina líder recebe a mensagem, o líder escreve estou sabendo; vou mandar replicar. 

O líder avisa, escrevi aqui vou mandar replicar.

Assume que já foi escrita a mensagem, que alguém poderá consumi-la, mas nesse meio tempo o líder cai, está escrito no disco, mas o líder não sincronizou com as réplicas e as réplicas não ficaram sabendo da mensagem que chegou.

Daqui a pouquinho uma das réplicas percebem que o líder está fora do ar e uma delas vira líder com as mensagens antigas, sem aquela mensagem. 

Tem duas situações que eu citei – uma em que as réplicas caíram e outra em que o líder e as réplicas vão se tornar líderes sem ter as informações mais recentes.

Porque nós que enviamos uma mensagem, HttpEcommerceService, quando enviamos a nossa mensagem, no send, chamamos um get e ele retornou quando o líder ficou sabendo que estava tudo ok.

O mais seguro é esperar o líder mandar para as réplicas e elas confirmarem, é lento. 

Porém você tem mais garantia, um pouco de troca da velocidade com as garantias que você quer ter. 

Eu quero ter garantia que tudo é executado exatamente numa ordem, então tem de ser serializado.

Um depois do outro. 

Eu quero ter garantia de que se um cai, as informações estão em outro lugar, então quando você envia a mensagem você só vai ter o ok, quando a mensagem for replicada nas réplicas. 

#### configurar os ACKS
Configura isso no Dispatcher, tem uma propriedade, set properties, producerConfig.ACK, os oks do servidor, quantos eu quero ter? 

O número de acknowledgments que o producer quer do líder para ter certeza de que o request foi completado.

Eu quero quantos? 

##### ACKS = 0
Aqui você tem os valores que você pode colocar acks = 0 se você for certo pode ser um arquivo de configuração ou a gente conversão, então **o produtor não vai esperar, manda mensagem e nem espera o líder dizer que está ok**. 

Há mensagens que se você realmente não vai lidar, não vai se preocupar.

Não estou nem aí se eu devo escrever ou não só quero mandar mensagem e perder uma outra azar. 

Pode ser, pode ter situações assim, você vai por zero e é mais rápido, você sai processando, ele vai automaticamente adicionar no socket e acabou; vai ser considerado que foi enviado, nem necessariamente foi enviado.

**Não existe garantia de que o servidor recebeu**. 

E a configuração de retries que o servidor fica retentando se não está lá vai ser ignorada, porque se ele não recebeu, tudo bem, é isso que você está dizendo com acks zero.

Em geral você não fica sabendo das falhas. 

##### ACKS = 1
Existem outras configurações, acks=1, quer dizer que **o líder vai escrever no log local, o líder não vai esperar que as réplicas tenham recebido a mensagem e confirmado**. 

Um significa isso.

Se o líder, recebeu, gravou e falhou, as réplicas não ficaram sabendo, será perdida essa informação. 

##### ACKS = all
E acks igual a all significa que **o líder vai esperar todas as réplicas que estão sync, todas elas estão sincronizadas, todas elas receberam essa informação, agora eu posso confirmar que a sua mensagem foi enviada**.

Se líder cair as réplicas têm essa informação. 

Quero usar acks config all, eu vou esperar todas as réplicas terem essa informação. 

Uma configuração super simples provavelmente o seu use case padrão vai querer isso, porque você provavelmente quer garantia de que se um cair está com o outro a mensagem está na réplica.

Você pode deixar o seu programador sobrescrever essa propriedade da maneira que quiser. 

Você quer um valor padrão para toda a empresa, para todo seu projeto. 

No nosso caso eu quero garantir que a mensagem esteja em pelo menos mais dois lugares, três lugares.

Porque os meus tópicos têm por padrão replication factor três, dessa maneira garante, só isso porque que agora o send.get vai esperar o acks do líder - falar as réplicas já foram sincronizadas, na verdade pode parar, reestartar o nosso HttpEcommerceService, agora ele já está usando essa configuração nova e vai esperar as outras réplicas.

Se rodar uma vez, new order send, vou olhar o tópico de New Order está aqui, está no líder um, dois, três, e logo depois derrubar o três, fiquei só com um então só vai estar na um. 

Enquanto está na um, a única que está em sync é ela. 

O líder virou a um. 

Agora eu posso tentar enviar uma nova mensagem, enviei.

O que aconteceu foi que ele enviou e quem estava em sync era a um, recebeu essa mensagem. 

Se tentarmos ver o consumer groups, bin/kafka-consumer-groups, todos os grupos de 90 e 92, 91 está de correto. 

Temos vários consumers grupos.

Por exemplo, o CreateUserService que está consumindo EcommerceNewOrder, partição zero, um e dois. 

O offset está zero mensagens. 

Por mais que esteja pedindo acks all, significa que todas as réplicas que estão em sync elas vão esperar, wait for the full set of in-sync replicas to ackowledge the record.

Estou esperando quem está em sync confirmar. 

Apenas uma está em sync, se só tem uma, ela confirmou, as outras pessoas já consumiram, se mandar de novo, estava na cinco, consumer groups, vamos ver agora current offset, seis.

Já consumiu as seis, então o ponto seria se ela escrever, mas não receber a confirmação de outro aí eu quero saber o que vai acontecer. 

Eu posso tentar forçar levantar a dois, vai voltar ficar em sync nesse topic, aqui uma 2 em sync, a líder é a 1 e a 2 em sync.

Eu posso enviar mensagem, mas já está derrubando o servidor, então vou pegar esse meu colega vou mandar um groups para ver a situação, zero, seis, seis, dou stop e refresh, a mensagem foi enviada. 

Agora que derrubei, o dois nem está aqui, mas a mensagem foi consumida.

Foi consumida porque estava na 1 e a 1 enviou. 

A um tinha informação para que o nosso consumidor consumisse. 

Espera mais máquinas consumirem isso, dessa maneira, com o acks garante um pouco mais. 

Mas para acks fazer sentido, ser valioso não dá para ficar rodando com uma só máquina e as outras paradas.

Quando uma máquina cai você quer o mais rápido possível levantá-la, quando o serviço de um consumer group etc. você quer o mais rápido possível levantar, porque você levantando logo o acks all precisa confirmar as três, não só uma pessoa, se você deixa cair até para ficar uma, o acks all e o de um é a mesma coisa, não tem graça.

Porque só vai confirmar ele mesmo, não tem problema de uma das três réplicas caírem. 

Lembra que a réplica é fundamental, o número de réplicas que você tem é a garantia que você vai ter se você colocar acks all, se seu acks for de um só mesmo tendo réplicas, você vai ter situação que a garantia é de uma só.

Resumindo se eu tenho n réplicas, eu só vou ter a garantia das n réplicas se o meu acks for all, se for um eu tenho n réplicas, mas eu vou receber uma confirmação sendo que só uma delas ficou sabendo, mesmo no acks all pode acontecer isso, mas só se todas exceto uma estiverem de pé e isso raramente vai acontecer.

Por que quando uma cai você levanta ela de novo, raramente vai acontecer de você ter uma única máquina de pé para um consumo grupo e uma partição. 

Você quer, coloca mais rápidas, quer ter cinco, sete, você vai perceber no seu sistema quantas precisa de acordo com o número de falhas que você vai ter que para que não caia na situação de uma única réplica.

Para que você sempre tenha pelo menos duas, rodando. 

Trabalhar com o número de partições, número de réplicas e acks all para o máximo de reliability de segurança de que os dados estão em algum lugar e serão consumidos.

## O que aprendemos?
* o problema do single point of failure
* a recuperação mais simples de um broker
* a recuperação e o rebalanceamento de um serviço
* como levantar mais um broker e rodar um cluster
* como efetuar a replicação
* o que é um líder
* a configuração do acks 0, 1 e all
* como utilizar garantias, reliability, velocidade, partições e replicação

# kafka: batches, correlation ids e dead letters
## Batch
### Simulando a geração de relatórios
Tenho vários pequenos projetos no meu sistema de e-commerce. 

Até agora, tudo que fizemos tem se baseado numa requisição que executa uma tarefa para um usuário. 

Até fizemos no servidor http uma classe capaz de trabalhar com uma requisição que cria uma nova compra.

Eu queria fazer um processo que executa várias tarefas de uma vez só. 

Uma coisa que podemos fazer é o relatório mensal que envio para os meus usuários. 

Se o e-commerce é de PDF de livros online, eu poderia gerar um relatório de leitura mensal. 

Se o usuário quiser, ele pode pedir esse relatório, e no final do mês geramos para todos.

Eu queria ver o processo de batch. 

Como executo vários de uma vez. 

O quão diferente seria. 

Vai ser bem simples. 

Primeiro, quero gerar um relatório de estudo. 

Vou ter um serviço novo. 

Crio em novo módulo. 

Meu padrão é serviço alguma coisa. 

Tenho meu relatório de leitura, que é o reading report. 

Dentro, posso criar meu serviço, que recebe uma mensagem dizendo querer um relatório para aquele usuário.

Como outros serviços que temos. 

Eu poderia usar um deles como base. 

Vou usar o fraude detector service. 

Ele usa o pedido de compra para detectar se tem fraude. 

Vou copiar o pacote inteiro. 

Primeiro, criamos, instanciamos esse objeto para inserir o report services. 

Tem que importar o projeto, adicionar dependência. 

Ele vai estar escutando pedidos de relatório. 

É user reading report. 

Agora é um pedido de geração. 

Vamos só receber os dados. 

Depois, gerar um relatório e fazer algo.

O relatório está ligado a um usuário. 

Temos a classe user? 

Ainda não. 

A classe está ligada a cada projeto. 

Esse user é desse projeto. 

Preciso aqui o user, em que vamos definir o id ou e-mail. 

Vou deixar apresentado como string.

Gostamos de simular essas coisas, para ver. 

Eu queria pegar um arquivo que tivesse o modelo do meu txt do meu relatório. 

Dentro do Resources, vou criar um novo arquivo. 

Seria informações básicas.

Vou pegar esse modelo, esse arquivo, que é um new file. 

Vou usar o path relativo para buscar. 

Vou chamar isso de source. 

Quero copiar esse arquivo para algum diretório.

Se eu dou enter, não existe o IO. 

Vou ter que criar em outro arquivo. 

Ele é baseado no usuário. 

O path para esse cara vai ser simples, vai ser o target, um arquivo com uuid mais report txt.

Quero copiar para IO. 

Ele recebe um path, um target. 

Quero garantir que esse diretório existe. 

Vou pegar o diretório pai e vou pegar os diretórios necessários. 

Depois, digo para copiar do sourcing para o target. 

Só que se já existir, vou pedir para sobrescrever.

Agora quero dar um append nesse arquivo. 

Quero adicionar uma linha. 

Falo files.write. 

Nesse arquivo, vou escrever o conteúdo e falo que quero adicionar o append. 

Seria o processo de gerar o relatório que você quer gerar.

Terminei de gerar o relatório, quero notificar os usuários. 

Eu poderia enviar um e-mail com esse relatório. 

Podemos fazer um processo.

Conseguimos ler e enxergar algo em disco. 

O pedido pode vir de um usuário só, mas eu queria fazer o patch. 

Vamos ter que fazer isso de alguma maneira.

Agora vamos precisar de outro serviço, capaz de gerar as mil mensagens. 

Vou fazer isso no http. 

Vou assumir que teremos uma parte de admin. 

Essa parte deve ficar onde? 

Não tem resposta exata. 

No nosso caso, vamos manter no mesmo servidor http.

### Generalização de processo de batch assíncrono e http fast delegate
Nosso próximo passo é ser capaz de receber uma requisição http que gera todos os relatórios. 

vamos ter um generate all reports servlet. 

E esse vai ser o admin generate.

Precisamos criar essa servlet. 

É o mesmo esquema que o new order servlet. 

Só mandamos o usuário que vamos gerar, então é um user dispatcher. 

Precisamos puxar a classe user. 

Nesse outro projeto ela só precisa do id.

No doGet, não vou ler parâmetro. 

Quero fazer um for para cada usuário dentro de todos os usuários. 

Vamos no nosso reading report services e vamos mandar nesse tópico o nosso usuário. 

Posso enviar o id dele, porque assim garanto que se tiver dois usuários com o mesmo id, eu ia executar um depois do outro. 

Mas se um usuário pedir três relatórios, eu executo o segundo relatório daquele usuário só depois de terminar o primeiro, assim um não tem preferência em relação ao outro sem querer.

Só falta ter acesso a todos os usuários. 

É verdade. 

Só que lembram que lá atrás eu falei de uma sacada do http que é importante? 

Você quer delegar para alguém, porque se de repente o servidor cai no meio, você gerou metade só. 

E não tem nem como controlar, porque a pessoa vai atualizar na tela dela. 

Enquanto na mensagem não. 

Como nós vamos gerar, temos mais controle.

O que fazer quando temos um serviço de http? 

#### fast delegate
Damos a resposta o mais rápido possível. 

Delega esse trabalho para alguém e dá uma resposta. 

Nós temos algumas abordagens. 

Uma seria já dar o ok e disparar uma única mensagem falando gere todos os relatórios. 

É o caminho.

Alguém vai escutar isso. 

Vai pegar todos os usuários e para cada usuário disparar a mensagem. 

Como nosso usuário final já sabe que o processo está sendo executado, não vai dar F5. 

Claro que o serviço pode parar no meio, e vamos lidar com isso de diversas maneiras. 

O que eu queria parar para pensar é quem vai ficar escutando as mensagens? 

Em qual desses projetos devemos colocar alguém escutando?

Poderíamos colocar no service reading report. 

Podemos colocar alguém que se chama generate all reports service, que vai escutar essa única mensagem e fazer o for para todos os usuários. Minha pergunta é se esse serviço tem acesso ao id de todos os usuários. 

Não tem. 

Como faço para ele ter?

Ou esse serviço pergunta para outro, faz uma requisição http, demora, mas recebe. 

É uma abordagem. 

Outro caminho é perguntar para outro serviço quais são os ids, só de maneira assíncrona ficaria esperando. 

Ele poderia enfiar a mão no banco de dados desse outro serviço create users. 

É válido. 

É feio, porque você criou uma ligação que não é mais só o esquema e semântica do tópico das mensagens. 

O que liga os serviços hoje é a semântica do tópico das mensagens, o que significa o tópico. 

A chave, que vai dizer se ele está em paralelo ou se é aninhado. 

E o esquema da mensagem. 

Se eu fizer isso, estou ligando atrás do banco de dados. 

Aí volto a trabalhar naquela história de todos acessarem o mesmo banco e seus problemas.

Ou eu poderia eu mesmo ter uma lista desses usuários. 

Toda vez que um novo usuário é criado, disparo uma mensagem dizendo que temos um usuário novo, com o id do usuário. 

Se eu tenho um serviço que quer manter atualizados todos os ids dos usuários, não tem problema. 

Você entraria em um problema de réplica se precisar apagar um id. 

Mas repare que o vínculo dos serviços passou a ser também o que eles estão interessados e outras coisas do gênero. 

É uma abordagem.

Vamos para outra. 

A quarta abordagem é fazer com que nosso generate mande uma mensagem que diz que para todos os usuários quero executar uma mensagem. 

Mando uma mensagem do tipo taskdispatcher, que vai ser executada para todos os usuários. 

Posso ter um dispatcher que vai executar algo para todos os usuários. 

Ele vai pegar e enviar uma mensagem para todos.

Eu vou fazer dessa maneira, para vermos tudo feito por mensagens. 

O tópico da mensagem vai ser o user generate read report. 

Tenho um único serviço que escuta isso, faz o for para cada usuário e para cada um envia essa mensagem. 

Se você tiver que gerar outra tarefa para todos os usuários, é só chamar esse aqui e a mensagem que você quer executar.

Dessa maneira, não mantive uma réplica de todos os ids comigo e não perguntei de maneira assíncrona perguntar para todos os usuários. 

Está sendo de maneira síncrona. 

Não vou perguntar, vou notificar.

Vamos precisar do dispatcher. 

Repare que não é mais de usuário. 

É simples, que manda o tópico. 

Vou chamar de batch dispatcher, que simplesmente executa para todo mundo. 

A chave vou usar a mesma, porque só tem uma.

Preciso em algum serviço que tem acesso ao banco receber essa mensagem. 

Posso criar um novo serviço aqui dentro. 

Esse cara vai precisar abrir uma conexão com o banco, do método main, do parse. 

No método main, ele cria o serviço.

Temos que tomar cuidado com o tipo de classe que ele recebe. 

Não iremos trabalhar com o usuário, mas com uma string. 

Quem também recebe string é o de e-mail. 

Quando eu receber essa mensagem quer dizer processem new batch. 

O batch é para o tópico. 

O tópico para o qual vou mandar tudo. 

Tiro o for dali e jogo em outro lugar. 

Para cada usuário vou enviar essa mensagem agora.

O fraude detector é um serviço que tem um dispatcher. 

A gente simplesmente criou um e usamos. 

É a mesma coisa. 

Ele vai para cada usuário, pega o id e manda gerar o relatório. 

Tínhamos colocado o user no http e-commerce, mas precisamos dele aqui no nosso service users.

Faltou pegar todos os usuários. 

Preciso de um getallusers. 

Fazemos isso da mesma forma que com o banco. 

Ele vai passar por cada um dos usuários. 

Só para lembrar, o users tem um campo chamado uuid. 

Então, seleciono uuid dos usuários. 

Só isso. 

Isso devolve para nós os results.

Vou ter uma lista de usuários. 

Poderia usar a lista de string? 

Poderia, você não precisaria de um modelo, funcionaria. 

A sacada foi que o http e-commerce service tem uma requisição possível que bate e envia diretamente o fast delegate. 

Para cada um dos usuários, quero que você mande a mensagem. 

Ele vai fazer um for para cada um e um send message. 

No body da mensagem quem vai estar é o próprio usuário. 

Ele vai invocar isso mil vezes, cem vezes, e para cada ele vai executar o gerador de relatório.

### Batch assíncrono em execução
Vamos testar o que a gente criou até agora. 
 
Para isso, vamos rodar alguns serviços. 
Vou rodar o http. Ela vai ter a requisição para gerar vários relatórios. 

Vou rodar o reading report services, e vou rodar o create user service. 

Vamos precisar do back send service, porque ele que fica escutando o tópico para mandar para todo mundo.

Tenho os três rodando. 

Além disso, nossos colegas vão tentar se conectar com o Kafka que está rodando agora. 

Vou testar. 

Ele enviou uma mensagem. 

Para cada usuário, ele enviou uma mensagem.

ele gera os relatórios, com o id do usuário para o qual ele foi criado. 

Tudo de forma assíncrona. 

Criei um serviço que replica para todos os usuários do seu sistema

### O que aprendemos?

* criando um novo serviço que faz IO
* consideramos o acesso a disco como serviço externo
* diversas formas de trabalhar batch
* usando o batch com http fast delegate
* usando um processo assíncrono e mantendo o isolamento do banco de usuários

## Serializaçao e deserialização customizada
### A importância de um CorrelationId
Por mais que estejamos trabalhando com várias seguranças, do tipo “consigo derrubar e levantar de novo”, existem várias situações de falha ainda. 

Vimos agora há pouco várias exceptions. 

Colocar o serviço no ar é uma coisa. Lidar com os erros é outra. 

Entender por que aconteceu uma exception dentro do reading report service, saber o caminho pode ser muito importante para saber o que está acontecendo com o sistema.

Eu gostaria de alguma maneira relacionar o código que está sendo executado com a mensagem que foi enviada, com a requisição http que o usuário fez. 

Eu queria que tudo fosse sempre armazenado na mensagem, porque quero ter certeza de que toda a informação está presente para mim a qualquer instante. 

Para entender o caminho inteiro, temos que de alguma maneira ir passando essa informação para frente. 

Eu queria de alguma maneira falar que estou com um id x, porque a requisição é tal, e o número é passado para lá. 

Com a requisição e a mensagem é o que gera a mensagem. 

O que vai chegar no reading report é a informação.

Para fazer isso, tenho um desafio. 

Preciso que esses caras tenham um id. 

Não só um id simples, mas que crie uma relação entre todas as mensagens. 

Vamos criar o correlation id, uma boa prática, em que vamos concatenar informações.

### A serialização customizada com correlation id e um wrapper
Vamos tentar implementar um correlation id para o sistema.

Primeiro, vou sair fechando tudo. 

Quero algo que vai ter em todos os serviços. 

No common Kafka vou ter uma classe que vai representar o id de correlação. 

Meu correlation id vai ter um id. 

Vou ter que gerar um construtor. 

Meu id vai ser um número genérico, aleatório. 

Agora, vamos pegar um primeiro serviço que tem que enviar uma mensagem. 

É o serviço de http. 

Se ele envia uma mensagem, o envio está no send e vai enviar uma string.

Eu posso alterar a classe string e colocar o correlation id ali dentro? 

Não. 

Mas em outras classes eu poderia. 

Poderíamos usar um sistema de cabeçalho. 

Também poderíamos criar uma classe que envelopa uma carga, que é uma string, com o que eu quero a mais. 

E aí tenho o controle fino daquela capa. 

É o que vou fazer.

Vou criar uma nova classe, que vai representar uma mensagem. 

Ela é construída com um correlation id, e um conteúdo. 

Só que a mensagem é tipada, é do tipo t. 

Então meu conteúdo é do tipo t. 

Vou jogar isso nos parâmetros. 

Tenho tanto id quanto payload na minha mensagem.

Como de costume, vou escrever meu toString. 

No correlation id também. 

Voltamos para quem vai enviar a mensagem. 

Quando estou mandando, mando um t, mas ao invés de enviar de verdade um value, poderíamos chamar isso de payload, e aí nossa mensagem é o t. 

Claro que o send não funciona agora.

Já sou capaz de enviar. 

Todo mundo já está enviando uma message de t. 

Tudo que eu fizer support à message, todo mundo vai ter support. 

Isso no envio. 

Mas temos que tomar cuidado com as propriedades. 

Temos que usar um serializador nosso. 

Nosso JSON não tem suporte à classe message, porque ela é genérica. 

Ele não sabe o tipo que tem aqui. 

Precisa de algumas dicas. 

Queremos customizar o processo fino. 

Temos que dizer como esse JSON vai ser representado.

Por enquanto, ele serializa um JSON puro. 

Podemos pegar esse service, levantar ele e levantar o log, para ver como está sendo feita a serialização da mensagem.

Primeiro vou ter que ir no terminal ver se meu Kafka está rodando, porque as mensagens estão incompatíveis. 

Vou matar tudo, porque vou querer rodar do zero. 

Vou entrar no diretório e iniciamos tudo.

Vamos dar uma olhada nisso acontecendo. 

Vou rodar o http e-commerce service. 

Mas na verdade vou debugar. 

E aí vamos chamar o generate reports, que vai tentar serializar a string para nós. 

Temos o objeto, que é uma message. 

Mas e quando eu serializar? 

Ele devolve o que seria o resultado. 

Parece que serializou, mas tem um cuidado que precisamos tomar. 

Repare que ele serializou o id e o payload. 

O payload é o quê? 

Que tipo ele é? 

Não sabemos. 

Quando a message é serializada ela não diz. 

Ela só armazena nome do campo e valor.

O que queremos fazer agora é customizar o JSON, adaptando, registrando um type adapter. 

Ele vai ser para classe message.class, vou registrar um adaptador, que vai ser meu new message adapter. 

Ele implementa o JSON serializer de uma mensagem. 

Queremos implementar esse método. 

Ao invés de retornar nulo, quero preparar um objeto, um JSON object.

Muito cuidado. 

Temos que retornar esse objeto. 

Vou falar para ele que vou adicionar o payload, que é o meu contexto de serialização. 

Já nosso id, que é o correlation id vai ser o content message get id. 

Faltou o tipo. 

Vou adicionar uma propriedade, que vou chamar de type. 

Vou pegar o payload, a classe dele e dar um get name. 

Você poderia fazer de outras maneiras, mas estou pegando a classe do objeto que estou serializando.

Vamos dar uma olhada no resultado. 

Payload é uma string e o correlation id é esse. 

A serialização está bonita. 

O que falta é a desserialização.

### Deserialização customizada
Nosso próximo passo é a deserialização. 
 
Assim como fizemos o JSON serializer queremos o deserializer. 
 
Primeiro, quando criamos o builder, os dois vão usar o mesmo type adapter. 
 
Segundo, quando deserializamos, estou deserializando o quê? 

Estou sempre deserializando uma message. 

**Eu não deserializo outra coisa de jeito nenhum, porque só enviamos message**.

Tem maneiras e maneiras de trabalhar, vou fazer dessa mais simples primeiro. 

O deserializer vai transformar numa string e vai mandar transformar em JSON, para o tipo message.class. 

Isso devolve uma message. 

Na prática, esse JSON só deserializa mensagens. 

Fica bem mais simples. 

A implementação da deserialização em si está dentro do message adapter.

Poderia colocar em classes distintas? 

Se o código é grande. 

Na deserialização, temos que ler esses três valores. 

O type do payload, o objeto do JSON. 

A propriedade do get as string é o payload type, o nome da classe. 

O correlation id é um objeto. 

Vou mandar deserializar. 

Só que ele não sabe qual o tipo. 

Eu falo para ele. 

Por fim, nosso payload é um context deserializer.

Tem um cuidado para tomar. 

A classe pode não existir. 

Se não existir, vou jogar um JSON parse exception. 

Tem um detalhe importante. 

**Essa classe pode abrir também um buraco, porque alguém malvado pode enviar uma mensagem dizendo uma classe do sistema que você não espera e você vai dar um class for name nessa classe**. 

Talvez você queira uma lista de classes que você aceita.

Vou retornar uma nova mensagem, porque já tenho o correlation id dela, e tenho o payload, é só devolver as coisas na ordem certa. 

Falta testar isso. 

Quando chamarmos, ele vai startar o service users que envia para todo mundo. 

Esse cara que envia para todo mundo vai precisar de alguma maneira fazer o código de receber a mensagem. 

Temos um Kafka service, que já recebe a mensagem, mas não muito bem.

Estou falando que quero um Kafka service, Ele é um Kafka service de strings. 

Nossa consumer function é de string. 

Ela tem que receber um consumer record de message de string. 

Agora o record é a mensagem. 

Minha mensagem é o valor que está lá dentro. 

Se eu pegar da mensagem o get payload tenho a string.

Quero debugar, porque o JSON deserializer usa o adapter. 

Posso olhar a string da mensagem. 

Vamos ver se a deserialização funciona. 

Tenho meu objeto. 

Vou dar um stop, rodar de novo. 

Ele vai consumir a mensagem. 

Se ele enviou, o service reading report vai receber as mensagens. 

Só que todo nosso consumer vai receber um message de user. 

Se eu tirar o message de t, em um lugar funciona, mas em outro reclama. 

Tem que ser tudo message. 

O usuário é o payload, e o resto continua funcionando.

Ele está recebendo vários, sempre com correlation id, com payload, que é um usuário. 

Está funcionando. 

Podemos colocar um toString. 

Ele vai imprimir o id do usuário.

Funcionando. 

Só que o correlation id não está tão interessante, porque cada mensagem nova tem um correlation id diferente. 

Não era o que eu queria. 

Eu queria que se começou numa requisição um http e-commerce service, todas as mensagens disparadas a partir de então tem que ter a palavra http e-commerce service, o mesmo código aleatório, para dizer que todas saíram da mesma requisição. 

Temos que implementar isso.

### O que aprendemos?
* a importância de um correlation id
* serialização e deserialização customizada em sua própria camada
* wrapping de mensagens com tipo próprio

## Correlation ID
### Implementando o correlation id
Chegou a hora de implementarmos de verdade o processo do correlation id. 

Agora que já temos o invólucro em volta do payload, queremos que o correlation id realmente indique o fluxo da mensagem dentro de todos os meus serviços. 

Vamos parar para pensar no primeiro ponto de todos, o http e-commerce service.

Quando mando a mensagem do generate, eu gostaria que essa mensagem tivesse um correlation id que começasse com a palavra generate report service, por exemplo. 

E claro, cada vez que chamo o do get, ela teria essa palavra e um número único. 

Às vezes esse número único tem informações do time stamp, para que você possa usar hora na busca.

Quando criamos o dispatcher, falamos qual é o código dele. 

O identificador único. 

É o nome da classe. 

Vou usar o simple name, que é só o nome simples da classe. 

Esse nome vou atribuir a uma variável membro. 

Também quero usar esse nome quando crio o correlation id. 

Esse é o name do meu dispatcher. 

Então, para o correlation id não é mais name. 

É tipo um título. 

O id vai ser o título mais alguma informação. 

Uso um parênteses para indicar. 

A mensagem que ele manda sempre vai ser generate all report services e um número aleatório. 

Dessa maneira garanto isso.

Como sabemos que as mensagens chegam? 

Vimos elas chegando, no log service. 

Ele antes recebia uma string, agora vai receber um message de string. 

Queremos uma consumer function que receba uma message de string.

Posso imprimir tudo, como antes, mas vamos testar. 

Temos quatro serviços rodando. 

Posso startar o generate reports. 

Ele me dá aquelas mensagens e não chega nada no log service, porque ele só escuta. 

Faltou colocar todos os tópicos. 

Essa é a importância de manter um padrão. 

Da maneira como enviei o generate all reports, ele recebe no tópico.

Ele está reclamando, porque como vai despachar, precisa falar o simple name, para startar. 

Depois, o que lê é o reading report service. 

Ele recebe uma message e consegue imprimir. 

Ele vai escutar o E-COMMERCE USER. 

Esse cara, quando está enviando, o tópico não é ele quem define. 

É o generate all reports.

Quem envia, precisa falar o nome. 

A order é o value get payload. 

Por fim, o log service. 

Mas você precisa criar os tópicos antes. 

Vou deixar ele rodar tudo, depois rodo o log service. 

Ele vai pegar todos os patterns e rodo de novo, porque quero que ele pegue as mensagens de e-commerce para mim.

A serialização e a deserialização estão certas. 

E o correlation id foi. 

Estamos colocando um título e um id. 

Está faltando que na segunda mensagem, na terceira e na quarta, elas tinham que informar a mensagem que as originou. 

Quem originou foi a primeira. 

Tem que estar concatenado.

Repare que sempre que estou enviando uma mensagem, não estou falando o correlation id. 

Estou deixando ele criar. 

Aí está a sacada. 

Se eu deixo esse padrão, as pessoas podem esquecer. 

Nesse caso, quero que todo mundo lembre, porque se uma pessoa esquecer perco completamente o log. 

Para de fazer sentido, desconecta. 

Não quero criar o correlation id aqui. 

Quero receber como parâmetro.

Se eu vou precisar dele como parâmetro, meu nome lá em cima não preciso mais, porque não estou usando mais o nome aqui dentro. 

Mas é importante entender o que estamos fazendo. 

O send precisa de um correlation id. 

Baseado nisso, ele vai enviar uma mensagem. 

Quando instanciamos, não precisamos mais disso.

Tem vários outros que não estão compilando, porque mudamos o construtor. 

Vamos ter que tirar a string e fazer isso funcionar de novo.

Agora forcei todos os meus desenvolvedores a criar um correlation id, para a pessoas não esquecer que é vital para o sistema. 

Quem quer usar o Kafka precisa usar o correlation id no sistema. 

Mas a pessoa não precisou concatenar no correlation id antigo os valores.

Esse meu novo correlation id não é para ser completamente novo. 

Ele precisa pegar da mensagem o id existente e continuar com esse título. 

Vou criar o método continue que recebe um novo título e vai retornar um new correlation id, com o meu id atual, mais um hífen e um título novo. Só que se eu colocar o título novo, ele mesmo vai concatenar. 

A partir de agora, todo mundo que recebe uma mensagem e dispara uma mensagem por causa disso vai chamar o continue if. 

Quem mais usa o Kafka dispatcher? 

Vou verificar. 

Se ele usa, em algum momento está enviando e vamos usar de alguma maneira.

Agora que eu mudei de message para order, o message é o record.value. 

Se ele é minha message, a order é a message get payload.

Temos todo mundo rodando e disparando as mensagens. 

Quero restartar esses caras.

Agora, o novo correlation id é o correlation original mais um id aleatório. 

Assim vamos concatenando. 

Conseguimos saber quem gerou quem. 

Se no meio você tivesse requisições http assíncronas também seria interessante passar o correlation id, para você saber por qual caminho chegou a requisição. 

Monitorar o que acontece fica mais complexo, mas o correlation id funciona.

### O que aprendemos?
* como implementar um correlation id
* a importância da mensagem como wrapper ou headers
* como manter o histórico de mensagens que geraram uma determinada mensagem

## Arquitetura e falhas atá agora
### Revisando a arquitetura até agora
Agora que já conseguimos trackear uma mensagem pelo sistema, queria levantar todos os nossos serviços e simular o que acontece quando um cai, o outro levanta. 

Vou passar serviço por serviço. 

O que é serviço, recebe uma message de string.

Além do e-mail service, temos o fraude detector. 

Tenho todos os seis serviços rodando em paralelo. 

No nosso terminal, podíamos dar um comando para ver como estão os tópicos. 

Ele vai mostrar a situação dos tópicos para nós. 

Por padrão, eles têm três partições. 

É replicado em três kafkas.

O que acontece quando levantamos alguém que vai disparar a mensagem? 

Ele dispara a mensagem recebendo confirmação do líder daquela partição, e aí essa mensagem é replicada para outras réplicas. 

Temos confirmações de que aquela mensagem foi replicada, gravada ou se não estou nem aí. 

São opções. 

Inclusive, no tópico consumer offsets, temos várias informações sobre os offsets de vários consumidores em relação a vários tópicos. 

Isso é feito pelo próprio Kafka.

Além disso, vimos muito tempo atrás o consumer groups. 

Ele vai mostrar todos os grupos de consumo, pedindo para descrever. 

Cada serviço que está escutando coloquei um grupo de consumo diferente. 

Por exemplo, podemos subir lá em cima e ele fala o grupo. 

Tem o batch send message service, que estava escutando o tópico e-commerce send message to all users. 

O consumer id nesse caso é o mesmo, porque só tenho um serviço dele rodando. 

Ele está escutando os três.

Repare que nosso batch send message service por uma época consumiu o tópico send message to all users. 

Temos cada grupo em cada tópico que está sendo escutado por esse grupo.

Cada tópico tem várias partições. 

Se tem três partições, elas estão sendo escutadas pelo menos serviço. 

Na partição zero, estou no offset, já li até a mensagem cinco, sendo que tem cinco mensagens. 

Então não estou atrasado. 

Porque não tem mensagem para desatrasar.

Poderia acontecer de parar um desses serviços. 

Agora posso rodar a requisição, que envia uma mensagem nesse tópico. 

Se eu dou o describe, vamos ver que o cara está parado, porque não tem ninguém ativo. 

Provavelmente um problema. 

Só tinha um e parou. 

Inclusive, na partição zero está no quinto. 

Se eu for lá agora e rodar de novo o batch send message service, vou rodar o consumer group de novo. 

Conseguimos aqui ter uma sacada de que se um serviço cai e recupera, ele consome as mensagens que estão atrasadas. 

Também vimos que se eu derrubar algum dos brokers, vou derrubar o broker que é o server três. 

Quando faço isso, o tópico que tinha o líder três para de ter líder três, joga a liderança para alguma outra pessoa. 

Tem réplica no três, porque tem várias informações lá. 

Mas quem está em sync é só um e dois.

Se eu levantar agora o três, ele vai assumir a liderança? 

Não necessariamente. 

Ele só vai voltar a ficar em sync com quem precisa estar em sync. 

Vimos que tivemos tolerância à falha, principalmente nos brokers, porque posso ter vários e derrubo um, e no serviço.

O que eu queria ver agora é vamos rodar duas vezes o mesmo serviço. 

A gente fez isso lá atrás, mas agora que aprendemos várias coisas de como funciona o Kafka, quero ver esse tipo de falha de novo.

### Revisando o rebalanceamento
Lá para trás, trabalhamos com o envio das mensagens. 

Quando fazemos um Kafka dispatcher, disparamos a mensagem e esperamos todo mundo confirmar para ter certeza de que foi. 

Quando criamos um Kafka service, falamos que queremos consumir diversas mensagens.

Por exemplo, a configuração do server, o key, o value. 

São essas coisas que estamos falando. 

Podemos falar de quantas em quantas mensagens queremos consumir. 

Quando faço isso, encontro vários records. 

O log service pode consumir, ele vai encontrando os registros. 

O reading report service pode consumir. 

Quando criamos o Kafka service, passo para ele diversas propriedades.

Como padrão, temos o Bootstrap servers, o key deserializer, o value, o group e o client id. 

O tipo não usamos mais. 

Ainda usamos o tipo para ter indução do tipo que estamos trabalhando. 

Eu poderia usar o consumer function. 

Agora, quem chama o Kafka service, não precisa mais passar string, porque nossa função parse recebe uma message de string. 

Ele pega do tipo do parse. 

Todos os lugares que chamavam o Kafka service não precisam mais.

Simplificamos nosso código porque o Kafka service não precisa mais do tipo. 

Ele recebe direto a função. Do jeito que queríamos.

O do tópico quem usa é o log. 

Por fim, vamos voltar para o read report service. 

É ele quem recebe diversas mensagens. 

Mas se dermos uma olhada, tem um momento em que ele recebeu 12 registros de uma vez só. 

Nosso poling do parse é feito no Kafka service. 

E aí o poling é feito por no máximo cem milissegundos, porque a cada cem milissegundos estamos falando para o servidor que estamos vivos. 

Enquanto o servidor souber disso, sei que tem alguém lendo naquela partição. 

É uma grande sacada.

Se eu tiver mais de um serviço rodando, vou rodar uma segunda vez. 

Pego o send message e copio. 

Posso dar ok e mandar rodar. 

Repare que ele vai dizer que estamos no e-commerce send message to all users escutando duas partições. 

Se formos no que estava antes, ele atualizou também, só para a partição dois. Aconteceu o rebalanço. 

Só vai acontecer quando o servidor está comunicando com o cliente, porque ele precisa avisar o cliente que levantou o segundo. 

O primeiro não sabe disso. 

O segundo avisa o broker, o broker fala e rebalança. 

Ele precisa da comunicação com os dois.

Queremos a batida de coração frequente e o broker sabe em quantas pessoas ele pode dividir, porque se em algum momento o segundo back send message service parar, ele para de pingar o servidor. 

Se isso acontece, o servidor fala que o cara parou e não tem mais ninguém escutando aquelas partições. 

Se não tem ninguém, rebalanço.

Essas são as grandes sacadas dos rebalanceamentos. 

Esse poling devolve para nós um número de mensagens recebido. 

Essas mensagens, podemos definir um máximo, porque por quanto mais tempo processo os reports, mais coisa pode acontecer e mais chance de erro.

É muito comum que em algum momento no nosso serviço tenhamos uma configuração padrão para o consumidor, que seja o número máximo que vou consumir por vez. 

É um. 

Uma mensagem por vez. 

Eu processo uma mensagem por vez, notifico meu broker, dizendo que avancei um. Posso ir avançando de um em um. 

Vou rerun e acessamos para ver as mensagens indo.

Comecei, gero e ele começa. 

Maravilha, ele vai um por um e notificando o servidor. 

Temos a revisão completa até do rebalanceamento.

### O que aprendemos?
* revisando tópicos e partições
* revisando consumer groups
* revisando líderes e réplicas
* revisando rebalanceamento

## Assincronicidade, retries e deadleatters
### Retries e assincronicidade
Falamos muito de como funciona tudo que criamos até agora em diversas questões do Kafka. 

Eu queria ver uma situação que não trabalhamos ainda, mas que também é importante.

Vimos no comecinho que quando enviamos uma mensagem, fazemos uma chamada do get. 

O get segurava nossa thread atual até ter uma confirmação do Kafka que a mensagem foi enviada.

Você poderia falar que nem sempre você quer isso. 

Às vezes você vai querer fazer de forma assíncrona. 

Eu poderia fazer esse envio de mensagem assim. 

Queremos que o trabalho seja assíncrono, mas a questão é que eu que estou chamando o send quero ir para a próxima linha mesmo sem ter certeza do que está lá? 

Se sim, podemos renomear para send and wait e criar uma função que seria assíncrona.

O problema é que desenvolvedores vão por padrão chamar o send e esquecer de dar wait quando você quiser, o que pode ser grave. 

Eu pessoalmente prefiro que por padrão o send espere. 

Temos um send que é assíncrono, não espera. 

Ele faz tudo, exceto o get. 

Quero extrair um método, que é o send assync.

Agora tenho como enviar um send normal e um send assync. 

Estou usando tudo no mesmo pacote. 

Se você quiser enviar a forma assíncrona, é assim.

Vamos usar isso quando fazemos o batch send message service. 

Antes ele enviava uma por vez e agora ele vai enviar de forma assíncrona.

E se o tópico que estamos usando cair? 

Meu único jeito de derrubar tudo seria derrubar todos mesmo. 

O que eu faço se tem broker quebrado? 

Eu posso levantar. 

Ele está enviando mensagens das outras partições. 

Foi de forma assíncrona.

O perigo é que enquanto ele está tentando enviar e não consegue, ele pode conseguir enviar para outra. E aí ele volta e envia para a primeira. 

Imagine que ele está tentando enviar para 0, não consegue, envia mensagem para outra, tenta para a 0 de novo. 

Ele consegue, mas não recebeu confirmação da outra. 

Pode ser que tenhamos dado uma informação do futuro sendo que a anterior não foi.

#### in flight requests per session
Como ele faz os retries, existe uma configuração chamada in flight requests per session. 

Retries é o número máximo de vezes que ele vai tentar enviar a mensagem. 

Se você tiver um retrie válido e configurar um max in flight requests, a ordem dos commits pode ser diferente. 

Você pode ter enviado primeiro A e depois B, mas B chegou antes.

#### garantias do Kafka
O Kafka tem algumas garantias. 

**Ele não garante que as mensagens vão ser processadas na ordem de envio, mas ele garante que na ordem que ela aparece dentro da partição é a ordem que vai ser consumida**. 

A questão é se vai chegar na ordem em que enviei. 

Não dá para saber.

O **max.in.flight** vai dizer que **o número máximo de tentativas paralelas é n**.

Corremos esse risco. 

No nosso caso, processar um usuário antes do outro não vai ter grandes problemas, mas às vezes você vai querer garantir algo do gênero sim. 

É uma propriedade importante. 

Tem várias propriedades interessantes para estudarmos à medida em que avançarmos.

### Enviando mensagem de deadletter
Vamos dar uma olhada em outra situação de falha comum. 

Imagine que temos tudo acontecendo, mas quando consumimos uma mensagem dá erro. 

Qualquer erro. 

Se eu forçar um erro, o que posso fazer? 

Se meu programa está rodando, já era. 

Ele já consumiu a mensagem.

Quando consumimos uma mensagem, eu gostaria de alguma forma de falar que deu erro. 

Vou abrir o Kafka service. 

Ele chama o parse. 

Eu tinha falado para tomar cuidado. 

O que eu quero agora é além de logar registrar que aconteceu o problema. 

Como posso fazer isso? 

Mandamos uma mensagem síncrona para um tópico. 

E aí falo qual foi o problema que aconteceu. 

Vou precisar da chave. 

Uso o correlation id.

Agora precisamos de alguma maneira falar qual foi a mensagem que deu caca. 

Só que esse objeto pode ser de qualquer tipo. 

Eu vou criar um JSON serializer genérico e vou mandar serializar minha mensagem.

Falta criar um dispatcher. 

Vou chamar de deadtletter. 

Vou ter um try onde vou criar o dispatcher. 

É um deadletter new Kafka dispatcher que vai enviar conteúdos do tipo string. 

Mas não precisamos mais disso. 

É só jogar direto.

Pode dar um exception na hora de enviar essa mensagem. 

Se isso acontecer, quero parar o programa. 

Eu tinha que tomar uma decisão. 

Se dá erro no registro do deadletter, continuo com a próxima mensagem ou mato o serviço? 

Se eu simplesmente logo a informação, ela vai ficar guardada.

Eu preferi parar tudo. 

Mata o serviço. 

Poderia tentar de novo, consumir de novo, até dar certo, pode ser, tem vantagens e desvantagens. 

Se o deadletter não der certo, para tudo porque tem algo errado. 

Essa exception vou jogar no método main.

Conseguimos logar uma mensagem de deadletter. 

Ele acontece, porque no Kafka service estamos enviando um e-commerce deadletter.

Todos os serviços que consomem também precisam do throws. 

Com isso, criamos um sistema de deadletter.

### O que aprendemos?
* como verificar os retries
* onde estudar configurações importantes do produtor
* como implementar um dead letter simples

# Kafka: idempotencia e garantias
## Organização e lidando com múltiplos tópicos de envio em um mesmo serviço
Vamos continuar o nosso projeto.

Você poderia argumentar que eu gostaria de ter de repente uma biblioteca comum para acessar banco de dados, pode ser que todos os bancos de dados usem o mesmo banco, parece ruim, cada um tem o seu banco nos meus serviços, parece ser o tradicional, cada serviço tem o seu banco, mas a camada de código que abstrai o acesso ao banco pode ser uma biblioteca commom, seja lá o que for de DataBase.

Temos esse tipo de bibliotecas commom e depois vários serviços, alguns serviços que são pontos de entrada do nosso cliente conosco, por exemplo, o nosso service-http-ecommerce o que é quando a pessoa faz requisições http, também temos o service-new-order que a pessoa executa um programa para valer.

Eu poderia ter também outras requisições http, seja lá o que for, que seriam pontos de entrada automáticos, como um API REST ou algo do gênero, que são outros programas se comunicando comigo, um aplicativo, um celular se comunicando comigo, são serviços que são pontos de entrada no meu grande conjunto de serviços.

Tem os serviços que ficam lá no miolo, o fraud detection, ele recebe uma mensagem e ele produz outras mensagens, tem alguns serviços que são meio que utilitários, por exemplo aquele que roda um Batch, o serviço de Users, tem dois serviços lá dentro, um que roda um Batch de envio de mensagens, ele recebe uma mensagem que ele tem que replicar para todos os usuários, ele republica essa mensagem em outro tópico para todos os usuários, por exemplo.

Eu tenho também serviços que são uma “camada final”, eles se comunicam com sistemas externos, por exemplo, temos um serviço na parte de e-mail que envia um e-mail, ele se comunica com um sistema externo SMTP para enviar e-mail, não implementamos o SMTP aqui, mas você faz como você quiser para acessar a API SMTP, ou um serviço de e-mail que você utiliza, MailChimp, seja lá o que for.

Também podemos ter serviços que fazem requisições para outros sistemas http, para outros sistemas com outros protocolos, podemos ter serviços que armazena o log em disco, eu estou considerando o disco como uma coisa externa, ou que armazena um Log em um sistema externo, tudo isso são maneiras de nos comunicarmos nesse nosso universo de serviços.

Agora eu quero dar uma organizada nesses pacotes do nosso common-kafka, que é uma coisa simples de organizar, um conjunto pequeno de classes.

Seria razoável separarmos um pouco disso e tem duas formas que eu acredito que são mais comuns de separar, uma forma seria separar a parte de recebimento de mensagens com a parte de envio de mensagens, isto é, o que está ligado com serviço e com deserialização, colocar em um pacote, a parte que está ligada com o Dispatcher e Serialização colocar em outro pacote, uma maneira razoável de organizar.

Outra maneira de organizar seria separar a parte de Serialização e Deserialização, a parte que lida com o Gson da parte que lida com o Kafka em si, percebem as diferenças? 

Então uma parte vai trabalhar com o Gson, isso é uma coisa, outra coisa é a API do Kafka em si que são o KafkaDispatcher e o KafkaService de envio e recebimento de mensagem, você poderia separar de maneiras diferentes.

Eu prefiro separar da primeira maneira que eu citei e eu prefiro deixar o GsonDeserializer que é utilizado junto com o nosso KafkaService, eu prefiro deixá-los juntos no mesmo pacote, o GsonDeserializer junto com o KafkaDispatcher porque eles são usados no mesmo pacote, pessoalmente eu prefiro dessa maneira, de novo, não tem regra.

Eu vou criar um novo pacote, um deles eu vou chamar de “dispatcher” que é a parte que envia mensagens, a parte de envio de mensagens é o KafkaDispatcher com o GsonSerializer, serialização e o Dispatcher estão juntas, aí você fala: “E a mensagem?” A mensagem é geral então eu vou deixar geral, eu só queria dar essa organizada.

Vamos refatorar, ele comenta que tem vários conflitos de coisas que são locais e que vão ter que ser transformadas em públicas, realmente eu quero que esses caras tenham uma interface pública a partir de agora, vamos abrir esses dois caras? Vamos ver o que tem que ser público aqui.

Construiu um KafkaDispatcher, tem que ser público, o KafkaDispatcher pode ser usado em qualquer pacote, o método send eu quero que seja usado em qualquer pacote, fechei, a Message que está em outro pacote agora, também “Ctrl + Enter” eu quero que ela seja pública, o Dispatcher eu resolvi, o Serializer agora, o Serializer é público.

Agora vamos fazer a parte do serviço que consome, eu vou colocar um Package, vou chamar de “consumer”, dentro do consumer eu vou colocar o Service, o Deserializer e o ConsumerFunction que isso daqui só é usado no consumo, os outros são usados nas duas fases, na de ida e na de volta então eu vou deixar separado, estou movendo tudo para lá.

A interface ConsumerFunction é pública porque está escrito e o método é público porque é público, aqui está public, lembrando, aquela interface eu deixei um throws Exception que pode ser algo muito nojento, porém no nosso caso que é muito específico, queremos ser obrigados a tratar todo tipo de Exception e temos já todo tipo de Exception sendo jogado, vários tipos que herdam de Exception então acabou ficando Exception, por isso.

Nossa classe Service tem que ser pública por que podemos acessá-la de qualquer lugar, os construtores também, se era privado pode continuar privado, o run, quem é que chama o run? A gente ainda chama o run para começar a rodar o KafkaService, vamos deixar public void run.

Reparem que ele é um Closeable mas ela não é um runable, se ele fosse um runable o que podíamos fazer? 

Chamar uma nova thread nele e ele chamava o run, dá para transformarmos ele em um runable? 

Até dá, o problema do runable é que não pode jogar Exception teríamos que tratar esse erro de alguma maneira para parar a thread, ela até para se der esse erro mas através de um Runtime Exception, poderia, não é o foco agora, estamos só movendo as classes agora e corrigindo o aspecto de acesso aos métodos.

Estamos com o pacote correto, eu posso dar um Build, Rebuild Project, para ele Rebuildar o projeto inteiro e temos isso refatorado, aqui ele reclama porque nesse caso específico faltou passarmos um CorrelationId, vamos passar um Correlation Id? Lembra, NewOrderMain está começando, se está começando eu vou querer um CorrelationId novo, então vou passar um “id” novo, como esse id vai ser usado? Para essas duas mensagens, para mensagem do Order e do emailDispatcher.

O nosso “id” é um “new CorrelationId” e qual é o nome mesmo? É o nosso “NewOrderMain.class.getSimpleName”, reparem que se eu usar dessa maneira aqui, o que acontece? 

Ele vai gerar um id aleatório, mas na hora que formos mandaram o send, vai ser exatamente o mesmo id para os dois, exatamente, eu não queria, eu queria que fosse um pouco diferente deste para este.

Por quê? 

Para identificar que este está ligado com esse tópico e esse com esse outro tópico, então aí sim está uma refatoração que eu gostaria de fazer agora e a refatoração que eu gostaria de fazer é a seguinte: 

quando eu envio a mensagem, Até usamos um CorrelationId e se der uma olhada no CorrelationId, temos como concatenar com mais alguma coisa usando o continueWith.

O que eu vou fazer? 

Percebam que estamos em uma situação no NewOrderMain que foi rodado, para essa rodagem do NewOrderMain eu criei um CorrelationId que é único, mas eu preciso Identificar qual é o tópico que estou usando e mais ainda, se eu tivesse mandando três mensagens de emailDispatcher eu queria identificar o tópico e qual dessas três está sendo usada.

O que eu vou fazer? 

Na hora que eu chamo o send, eu tenho o meu CorrelationId já com o nome do título e um Id aleatório, vou concatenar nesse meu Id, eu vou “continueWith” e eu coloco o próprio tópico, só que para separar o que é o tópico e o que não é o tópico, porque lembra, o continueWith vai colocar um hífen, para identificar que é um tópico e não um processo que está executado, eu vou colocar só um “”_ “ + topic”, o ID desse envio dessa mensagem e vai o próximo serviço.

Vamos tentar um Build, Rebuildar tudo, está Rebuildando, ele vai projeto por projeto, da base para todos os outros e todos os projetos estão Buildados, o que eu vou querer fazer agora? 

Fizemos uma pequena refatoração de pacotes e demos uma olhada no CorrelationId e em um cenário que ainda tinha um problema que é, quando eu tenho vários envios de mensagem de dois tópicos distintos na mesma invocação daquele serviço, ia ficar com o mesmo id nos dois casos, eu não quero o mesmo id nos dois casos.

Agora eu tenho id sempre únicos, inclusive Se eu mandar dez vezes a mesma mensagem, se dentro do NewOrderMain o que eu fizesse era enviar 10 vezes, o que eu vou ter? 

Dez CorrelationId e aqui vai ter um primeiro começo igual, o tópico, um valor aleatório e vai para frente.

Qual é o meu próximo passo? 

Discutir um pouco o envio de e-mail, eu quero discutir agora com vocês essa nossa camada final, como podemos estruturar isso e como isso costuma ser estruturado.

Quando eu tenho um serviço externo, por exemplo e-mail, poderia ser Analytics, poderiam ser várias outras coisas que podemos ter, mas um caso clássico é e-mail, que é um sistema que temos uma comunicação razoavelmente complexa.

Temos que criar o conteúdo do e-mail, temos que criar o subject, temos que enviar esse e-mail para uma pessoa, ou para todas as pessoas, talvez customizar o email por pessoa, talvez o e-mail seja transacional, um pessoa efetuou a compra, talvez seja um email de marketing de um dia, hoje é dia 01/01 então eu quero dar feliz Ano Novo para todo mundo.

Tem tipos de e-mail, situações, queremos deixar isso espalhado por todos os serviços, queremos concentrar isso, como queremos lidar com isso? 

Vamos discutir isso daqui a pouco, Como podemos fazer isso com serviços e mensagens.

### Micro serviços de email e fast delegate real
Continuando nosso projeto, eu queria discutir um pouco a partir do e-mail, repare, o nosso service-email foi feito de uma maneira que, qualquer parte dessa grande aplicação, envia uma mensagem e já dispara um e-mail, nesse instante o e-mail é só uma string, mas poderiam ser quatro strings, de quem, para quem, os tópicos, o subject e o corpo, o body, pelo menos esses quatro poderiam compor um e-mail e enviamos.

Mas você fala: “Guilherme, precisa mesmo de um serviço para isso? 

Não é só criar uma biblioteca chamada common-email e nessa biblioteca common-email eu crio uma classe chamada Email que recebe esses quatro valores no construtor, tem um método send ou algo do gênero, uma função em uma linguagem funcional, que tem o send, que recebe quatro strings e envia o e-mail de forma assíncrona?” 

Poderia ser, eu poderia implementar isso através de uma biblioteca, como fizemos com o CommonKafka.

Qual é a dificuldade que teríamos em lidar com isso? 

A dificuldade é que o e-mail é um sistema externo, por exemplo, mesmo sendo um sistema interno poderíamos ter esse problema, ele é um sistema externo, como nos comunicamos com ele, é importante.

Eu posso ficar mandando mensagens para esse cara que envia e-mail a cada segundo? 

Ou ele vai achar que eu sou um spam e é melhor eu enviar um limite de 100 por minuto? 

Eu devo me comunicar com os servidores da Amazon ou do Azure? 

Percebam que têm perguntas que talvez eu queira centralizar em um único lugar, se eu deixar esta biblioteca espalhada em todos os projetos, se eu tiver que fazer uma mudança dessas, eu tenho que mudar todos os projetos.

Eu não estou usando mais o serviço de e-mail da Amazon, eu tenho que usar o serviço do outro, não tenho como, enquanto todos não atualizarem a CommonLibrary do service-email1.0 para o 2.0, que só mudou a implementação da Amazon para o Google Email ou alguma coisa do gênero para enviar e-mails, já era, não vai, vai estar usando metade.

Agora se isso está extraído em um serviço de e-mail, fica transparente, se estamos usando servidores de SMTP próprios, se estamos usando os servidores da Amazon, servidores do Google, da Microsoft, da IBM, seja lá de onde for, não importa.

Por quê? 

O que me importa é, eu enviei uma mensagem para o meu serviço interno dizendo que eu quero enviar um e-mail, alguém que é responsável pela equipe de e-mail é responsável por lidar com essas partes e eu estou de boa, feliz e contente porque eu pedi para o e-mail ser enviado, o e-mail tem que ser enviado, é isso, tem uma equipe responsável pelo e-mail, claro, às vezes somos responsáveis por tudo, não tem problema.

Reparem que o problema da biblioteca é esse, todo mundo têm que migrar ao mesmo tempo, por quê? 

Porque eu estou utilizando provedores de serviços externos que eu tenho que migrar de um para o outro e eu dependo de migrações e lembrem, aquela sincronização, imagina em que eu tenho a 10 serviços, se eu tiver 300, eu não vou conseguir imigrar todos ao mesmo tempo, nunca, fica difícil de fazer isso.

De que maneira eu poderia fazer isso? 

Eu tenho duas maneiras pensando em um serviço de e-mail, uma é a maneira atual em que por exemplo, o meu cara que deseja enviar um e-mail? 

Quando temos um pedido de compra, logo de cara, na nossa Servlet por exemplo, já me envia um e-mail dizendo, quero enviar um e-mail e colocamos uma única string.

Lembram que eu falei que poderiam ser quatro strings em um e-mail? 

Poderia ser uma estrutura de dados Email com quatro Strings, nós criamos o texto customizado e mandamos para o EmailService e o EmailService dispara esse e-mail.

Na verdade preparamos tudo e só avisamos o EmailService, eu tenho isso para enviar, envie para mim por favor? 

E o EmailService envia, por exemplo, eu tenho um arquivo que eu quero disponibilizar para alguém acessar, se eu tenho esse arquivo que foi gerado na minha máquina e eu quero disponibilizar para uma pessoa acessar no e-mail, eu tenho duas abordagens.

Posso fazer isso via attachment, eu posso adicionar um attachment no e-mail e vai junto com o e-mail e posso fazer isso através de um link, isto é, eu tenho que pegar esse arquivo que eu gerei na minha máquina, subir para um lugar que vai ser público - ou não público, não sei, preciso estar logado, não precisa - e esse arquivo vai ser linkado dentro do e-mail.

Percebem que já vou ter vários passos que eu tenho que fazer, o que podemos fazer?

Ao invés de eu ficar gerando todo texto do e-mail, eu mesmo em cada um dos meus mil serviços, eu crio um tipo de serviço intermediário, que é o serviço que sabe, por exemplo, é o serviço responsável por e-mails em determinada situação, tem um serviço de e-mail que realmente só recebe os dados crus e dispara, porque com isso isolamos a camada externa.

Se você tem um serviço que tem que se comunicar com um serviço externo da Receita Federal, de nota fiscal, isola isso em um sistema, mas se de repente você tem 100 caras se comunicando com eles, talvez você queira extrair algum deles, talvez você queira isolar algum desses.

O que eu gostaria de fazer agora? 

Reparem que em alguns desses serviços eu vou querer enviar um e-mail ou em outros serviços eu poderia querer enviar um e-mail, ao invés de eu gerar um texto desse e-mail e enviar direto para o meu EmailService, eu simplesmente falo, eu tenho um novo pedido de compra.

Ele simplesmente fala ECOMMERCE_NEW_ORDER, acabou, não existe esse ECOMMERCE_SEND_EMAIL, por que não vai existir isso? 

Porque se isso existir aqui eu vou ter que, nesse serviço de NewOrder, me preocupar tanto com o processo de uma nova compra em si, quanto com uma questão de e-mail, como bolar este e-mail, são duas preocupações em um único serviço.

Vai ter gente que vai argumentar que temos que quebrar isso em dois, tem empresas brasileiras e lá fora que fazem esse caminho, então eu queria mostrar esse cenário, como eu quebro isso para ter em dois? 

Já tem alguém escutando o ECOMMERCE_NEW_ORDER, quem é que escuta o ECOMMERCE_NEW_ORDER? 

O FraudDetector, o log, o analítico, várias coisas podem escutar.

Eu posso ter um outro serviço, eu vou querer ter outro módulo e esse módulo é um service e o que ele vai fazer? 

Ele vai enviar um e-mail quando tem uma nova ordem de compra, quando temos um ECOMMERCE_NEW_ORDER, temos um service-email-new-order.

O que esse service-email-new-order faz? 

Você verá como ele vai ficar simples, ou pequeno, ele simplesmente faz o seguinte, vai ficar escutando um tópico, que nem o fraud-detector, ele é mais complexo até do que precisamos o fraud-detector, ele ficará escutando um tópico, como fazemos aqui, eu vou copiar o fraud-detector, vou copiar tudo, quando eu der o paste ele vai só pastear o pacote ecommerce, como eu imaginava, eu vou dar uma mudança manual br.com.alura.ecommerce.

Lembrando, eu poderia estar criando um pacote por serviço, organizar no serviço também pacotes, não tem problema esse eu estou deixando realmente no mesmo por enquanto, vamos mover não está querendo corrigir para mim, temos agora as duas classes.

Ao invés de FraudDetectorService o que eu quero fazer é renomear, eu quero renomear para “EmailNewOrderService”. 

Então o que o EmailNewOrderService faz? 

Ele vai criar o “emailService” para mim, emailService::parse, vai criar o KafkaService, eu tenho que adicionar a dependência, vai chamar o run, ele escuta o ECOMMERCE_NEW_ORDER.

E qual é a diferença? 

Ao invés dele fazer o que está fazendo agora, o que ele vai fazer é: pedir para despachar um e-mail, eu vou tirar essas linhas do NewOrder e o que ele faz é, quando ele recebe no meu parse, ele está processando uma nova ordem, então estamos “preparing email”, preparando o e-mail.

Eu vou jogar o value e o resto eu não vou imprimir, eu vou direto simplesmente fazer o quê? 

Despachar o meu e-mail, eu preciso de um emailDispatcher, eu vou deixar um aqui para mim, lembrando que o emailDispatcher é um Dispatcher de string, então eu vou enviar uma String, aqui eu tenho que ter o e-mail que eu vou enviar, que é o e-mail da pessoa, então é o “record.value()” e temos o “getPayLoad()” que é uma order e tem o “getEmail()”, temos tudo isso para pegar.

Como essa é a minha order, eu já vou extrair minha order, vou extrair “order”, aqui eu vou deixar o record mesmo que é a nossa mensagem, eu acho que é mais bonito e o que eu vou usar de Hash? 

Eu vou usar por usuário, “order.getEmail()”, como eu tenho o email, vou usar o e-mail do usuário como hash.

Desculpa, o hash do e-mail eu já estou usando aqui, o que faltou foi o CorrelationId. 

O CorrelationId é o quê? 

É o id da minha mensagem, continua com “EmailNewOrderService.class.getSimpleName()”, eu tenho o meu novo id, para esse id não ficar muito longo, eu vou extrair essa variável como “id” e para que isso também não fique, eu vou extrair esse record.value como a minha mensagem, essa é a minha mensagem, também pode ser um var.

Eu tenho uma mensagem, posso deixar tudo bonito, aqui ficou feio eu vou deixar um pouco bonito, o que esse service é capaz de fazer mesmo? 

Ele escuta agora a única mensagem que é enviada por ordem, teve um pedido de ordem, ele escuta uma mensagem, quando temos o pedido de ordem, agora temos aquele Fast Delegate de verdade, só dispara uma mensagem, acabou.

O que ele faz aqui dentro mesmo? 

Aqui dentro ele prepara o e-mail e pede para o e-mail ser enviado, quer dizer, se eu precisasse fazer upload de arquivos, faço upload de arquivos aqui, se eu precisar customizar esse e-mail de cinco maneiras diferentes, eu customizo de cinco maneiras diferentes, se eu precisar enviar um e-mail para o comprador, um e-mail para o vendedor, se de repente meu site é um Marketplace, tem o comprador e a compradora, o vendedor e a vendedora, eu quero disparar dois e-mails, disparo os dois aqui.

Percebeu a diferença? 

Eu isolei no meu serviço de e-mail, no meu serviço de nova compra, eu isolei todos os processos, inclusive o processo de preparar os e-mails, aqui eu só estou preparando os e-mails, seja preparar o e-mail para quem comprou, preparar o e-mail para quem vendeu, preparar o e-mail para seja lá quem for, eu isolei isso, se eu isolei isso aqui, lá no nosso HttpEcommerce o que tínhamos mesmo? 

Tínhamos uma Servlet que era uma nova compra.

O que podemos fazer ali? 

Arrancar fora o e-mail, por quê? Por que não despachamos mais e-mail, só despachamos ECOMMERCE_NEW_ORDER. 

Esse nosso emailDispatcher não precisa mais, não precisamos mais desse cara, posso fechar esse, posso fechar este outro, aqui também devemos ter um emailDispatcher que não é usado mais.

agora eu tenho um Fast Dispatch e o Delegate também pegou, delegou, estou feliz e eu isolei entre os meus serviços e um serviço de e-mail que é externo, um serviço intermediário.

De novo, não precisa ser para todos serviço externo colocar esse serviço intermediário, não precisa ser que só com serviço externo você vai querer este cara intermediário, você vai perceber se aquele código que você está fazendo, por exemplo, preparando 5 e-mails para serem enviados, faz sentido ser em ordem síncrona e sequencial, isto é, uma linha depois da outra ou não, distribui, se o e-mail for, foi, senão foi, não foi, pelo menos eu continuei o processamento da minha compra, mostrei a tela de sucesso e fui para frente.

Percebem as diferenças? 

Não estou preocupado, já comecei o processo da compra, se o e-mail de repente não foi, depois damos um jeito, quando você acessar o banco já vai estar lá, estou feliz, se eu armazenei no banco, disparo a mensagem e sigo em frente, ou só dispara a mensagem, a mensagem armazena no banco e sigo em frente, o que você preferir.

Tendo aqui uma mensagem que está registrada no Kafka, que eu sei que eu não vou perder, estou feliz com isso, Fast Delegate isolando o serviço de e-mail, uma abordagem bem comum, aí você fala: “Guilherme, eu tenho 20 tipos de e-mail no meu sistema, eu vou criar 20 serviços? 

Mais o serviço de e-mail, 21?”.

Essa seria essa abordagem, não é à toa que existem empresas onde se tem mais serviços do que desenvolvedores, por quê? Porque vários desses serviços são minúsculos, são tipo esse daqui.

Claro, esse daqui poderia utilizar uma biblioteca common-email que só te ajuda a criar aqueles quatro campos, o from, o to para deixar com o do usuário ou da usuária, para deixar o texto super redondo, poderia usar a biblioteca common para isso aqui onde fizer sentido, se é aqui ou se é no serviceEmail, você vai perceber onde faz sentido para você este tipo de biblioteca, esse tipo de ajuda.

Mas a sacada é, realmente vão ter muitos serviços minúsculos, que basicamente não tem consumo de CPU, de vez em quando vai precisar de CPU e é muito fácil de manter porque basicamente são uma ou duas classes que fazem alguma coisa bem específica de forma assíncrona sem nos preocuparmos, perceberam porque cresce bastante a quantidade de serviços quando você começa a trabalhar com realmente micro serviços? 

E aqui na mensageria podemos trabalhar dessa maneira.

## camada de serviços
### Extraindo uma camada de serviços
Nosso próximo passo é dar uma olhada nesses nossos serviços, lembram quando eu estava extraindo o código do serviço? 

Eu falei que os nossos consumers são Kafka Services que são Closeable, isto é, quando eu começo um serviço novo, por exemplo, o emailService eu falo para ele, cria um emailService que é essa minha classe, cria um KafkaService, fica no run dele e acabou, se der alguma Exception no meu run ele para o meu programa é isso.

Além disso, se eu quiser rodar dois emailServices ao mesmo tempo, o que eu tenho que fazer? 

Tenho que rodar o mesmo programa duas vezes ao mesmo tempo, em algumas linguagens que têm um baixo Impacto de memória e startup, isso é comum de ser feito, você faz o quê? 

Você roda um processo na sua máquina para cada instância do serviço você vai querer rodar junto com o Kafka.

Agora quando você tem uma linguagem que tem suas vantagens de compartilhar isso em diversas threads e desvantagem de ter, de repente, um Load Startup maior, você pode querer executar diversos desses serviços no mesmo processo da sua máquina, você pode querer rodar 10 emailServices dentro da mesma Virtual Machine por exemplo, do Java, dentro dessa máquina, poderia estar usando o closure aqui dentro da mesma Virtual Machine 10 instâncias desse serviço emailService rodando, poderia querer fazer isso.

O que eu gostaria de fazer? 

Tem duas abordagens diferentes, uma seria, reparem que a classe emailService não tem estado, literalmente eu poderia fazer que esse service.run fosse rodado 30 vezes, podia chamar 30 desse service.run que funcionaria? 

Não sei, porque o service.run está no KafkaService e não no emailService, quer dizer, o método parse, como não tem estado na classe emailService, maravilha, mas e o KafkaService? 

O KafkaService tem estado, tem o KafkaConsumer e tem o ConsumerFunction.

A ConsumerFunction, a nossa função não tem estado externo a ela que acessamos, não tem problema, mas o KafkaConsumer, se eu chamar duas vezes o poll, sempre podemos ir na documentação do Kafka, “KafkaConsumer” e “KafkaDispatcher”. 

Primeiro, no KafkaDispatcher que é despachar um e-mail, então “Java Class KafkaProducer”.

No KafkaProducer você vai procurar a palavra “thread” e você acha logo, é thread-safe, você pode fazê-lo em várias threads, usar o mesmo Producer em várias threads e o Consumer, eu posso usar ele em várias threads? 

Ele não é thread-safe.

E tem uma área só de Multi-thread, como podemos lidar com o Multi-thread? 

Tem algumas abordagens para trabalharmos com Multi-thread, ele dá algumas sugestões de como você pode trabalhar, um consumidor por Thread, desacoplar o consumo do processamento, isto é, você consome a mensagem e joga para outra Thread para processar por exemplo, você pode ter um consumidor em uma Thread rodando e consumindo.

Outra abordagem é: ela consome a mensagem e já fala: “Dispara na outra Thread o processamento que eu já vou para assumir a próxima mensagem.” 

Quer dizer, se der uma caca na outra Thread talvez você não notifique desse problema.

Então ele dá aqui vantagens e desvantagens dessas abordagens, claro, você pode fazer variações de tudo isso, uma vantagem de uma e a desvantagem que você pegou de cada uma dessas abordagens, o que nós vamos fazer? 

Não vamos compartilhar o KafkaConsumer entre duas Thread, de jeito nenhum e o nosso próprio serviço, o nosso EmailService, vamos criar um por um Thread.

O que eu queria fazer é que o meu EmailService na verdade estendesse de alguma coisa minha que é um serviço, de algum tipo de “Service”, eu poderia usar composição, eu poderia fazer várias coisas.

Qual será a sacada? 

A sacada é que o meu serviço vai ter que ter o meu método run, é o que roda tudo que tem que ser rodado, isto é, o que roda isso aqui, eu vou fazer com que o meu EmailService implemente uma interface do tipo “ConsumerService”. 

O que o ConsumerService vai fazer? 

Vou criar essa interface, vou colocar nesse projeto e depois extraímos, a interface ConsumerService vai ter o quê? 

Ela tem que ter uma função de parse, porque ela vai parsear alguma coisa, ela vai consumir alguma coisa.

Então ela é uma ConsumerService de “Strings”, então ele tem que ter um tipo que é o tipo que ele consome, mas não só um tipo que ele consome, precisa ter também o tópico que será consumido, eu preciso ter um nome “public String getTopic()” que é o tópico que eu vou ficar escutando, “return” esse tópico.

Aqui eu vou chamar simplesmente de “topic”, a função é a parse, não há problema algum, o que eu preciso é dessa função também ali, tanto a função quando o “String getTopic()” são duas funções que eu quero trabalhar, eu tenho o topic e eu tenho a minha “consumerFuncion”, essa daqui é a minha função de consumo que é essa daqui que é o meu parse, vou ter que chamar essas duas coisas, o topic e a ConsumerFunction.

Não estou passando parâmetro extra nenhum por padrão, eu tenho o meu KafkaService e eu tenho que falar qual é o título desse meu serviço que eu estou consumindo, qual é o meu consumerGroup, também vou precisar, “consumerGroup”, vou colocar também “public String getConsumerGroup()”, “return” isso daqui para mim.

Esse getConsumerGroup, a mesma coisa, vai estar onde? 

Na minha interface de um consumidor, todo consumidor vai ter essas três coisas, se todo consumidor tem essas três coisas, olha como ficará o meu método Main, vou cortar e o que eu vou fazer simplesmente é “new ServiceProvider”, eu vou criar um ServiceProvider e vou pedir para rodar, quem? 

Eu vou pedir para rodar vários EmailServices, eu quero rodar diversos EmailServices.

Como eu faço para rodar esses vários EmailServices aqui? 

Tem diversas maneiras de fazer isso, vou fazer dessa daqui, “EmailService” eu posso falar para ele qual é a função que cria um EmailService, é a função new, eu vou criar o meu ServiceProvider, a função run. 

A função run recebe o quê? 

Tem que receber uma “Function” que não recebe “NADA” como parâmetro e devolve um “ConsumerService”, essa é minha “factory”.

Por quê? 

Porque agora que tem essa Factory eu vou poder chamá-la várias vezes e criar várias instâncias no meu EmailService, vamos pensar, como eu quero uma função que não recebe nada e gera um ConsumerService eu vou criar uma Interface para isso, essa minha interface é um “ServiceFactory”.

O que o ServiceFactory faz? 

Ele tem uma única função, é uma interface funcional de Java que devolve um “ConsumerService” do tipo “T” e eu vou chamá-la de “create”, ela é uma “interface”. 

Então ela é uma ServiceFactory, eu vou criar essa ServiceFactory, eu rodo com uma do tipo t, eu tenho que falar que esse método recebe o tipo “”.

E o que eu tenho que fazer agora? 

Eu tenho que falar: “Factory, por favor, crie.” 

Quando ela cria, o que ela devolve para mim? 

Ela devolve o meu “emailService” e aí é só eu falar “emailService.getConsumerGroup()”, “emailService.getTopic()”, “emailService::parse” e eu vou chamar o meu run. 

O meu run pode dar uma exception enquanto eu estou rodando o meu serviço, não tem problema, Add exceptions to method signature, deixa jogar as exceptions, se eu for rodar eu deixo as exceptions rodarem.

Da maneira que eu fiz aqui eu estou mandando rodar um EmailService que está aqui, deixa eu ver o que faltou, jogar as exceptions. 

Com essa estrutura que eu fiz olha como ficou implementar um serviço, é só você implementar ConsumerService que são três métodos e você chama um new para executar ele, é só fazer isso, eu vou rodar uma vez, tem algum erro no final, a nossa função não pode ser privada, tem que ser “public”.

Os líderes estão disponíveis e eu estou feliz com todo mundo rodando, agora eu tenho meu serviço, repararam como ficou simples? 

É só eu definir qual é o meu ConsumerGroup, qual é o meu tópico e qual é o meu parse, eu vou querer melhorar isso um pouco, eu vou querer jogar isso para os outros serviços, mas antes eu quero rodar esse serviço 10 vezes.

### Paralelizando com pools de threads
Nosso próximo passo é, ao invés de chamar essa função uma única vez eu quero chamá-la 10 vezes e deixar rodando, eu quero rodar em 10 Thread diferentes, se essa função run implementar “Runnable” podemos dar uma nova Thread em cima dele e deixar rodando, podemos dar 10.000 Threads e deixar rodando, é uma abordagem.

Hoje em dia com as API mais recentes, já faz um bom tempo na verdade, a maneira educada é criar um executor, um fixer de Thread pool e mandar rodar neles, ao invés de eu fazer dessa maneira, o que eu vou falar? 

Eu vou falar, o que eu quero mesmo para esse ServiceProvider são vários EmailService:new eu quero vários desses, não vou querer um só.

Mas reparem que ele é Thread-safe, mesmo que eu instancie um único ServiceProvider, eu posso rodar isso quantas vezes eu quiser, já vou renomear essa variável para “myService”, esse é o meu serviço que é independente do e-mail.

O que eu quero fazer agora? 

Eu queria chamar essa função diversas vezes, ao invés do Runnable, é o “Callable” o que vamos fazer, se olharmos a interface do Callable o que rodamos é o call, a função é “call”, ela pode jogar exception, só tenham cuidado, ela devolve do tipo v e o tipo v você devolve aqui, eu vou falar que ela não devolve nada, é comum fazer isso void assim e eu tenho que dar um “return”, você pode decidir o que você vai devolver, vou devolver nulo, mas eu estou devolvendo void para nós.

Faltou agora que esse cara não pode receber nenhum argumento, ele tem que ser público e jogar exception, aquele não está sobrescrevendo, por que ele não está sobrescrevendo ainda? 

Por causa do t, tem que tirar esse t, agora sim.

Isso quer dizer o quê? 

Que é aqui que eu vou passar quem é o meu EmailService, quem é a minha Factory, eu vou falar, é esse que eu quero rodar várias vezes, o que eu vou fazer aqui? 

Criar o Construtor, essa é a minha Factory, eu vou criar o field aqui dentro, esse é o meu field, o ServiceProvider é do tipo t de Strings, ele vai trabalhar com Strings, esse cara pode ser “final”, então aqui está redondo.

Aqui eu estou passando o new e chamando o run, o run não era mais run, agora ele é call, até aqui tudo bem, estou chamando uma única vez, como é que eu faço para pegar um Callable e chamar diversas vezes? 

Podemos fazer o seguinte, podemos fazer um “for(int i = 1)”, tem outras maneiras de fazer esses for.

O que eu vou fazer? 

Na verdade eu vou pegar uma “rangeClosed”, a range fechada é um import do “IntStream.rangeClosed()”, eu vou do 1 ao 5, eu tenho várias maneiras agora de fazer o meu laço para criar várias thread e executar.

Uma maneira tranquila, eu vou usar essa maneira tranquila que eu prefiro misturar um pouco as coisas, eu vou criar um “newFixedThreadPool()” que eu vou falar qual é o número de thread que eu quero, esse é o meu número de “THREADS”, esse é o meu pool.

E o que eu vou querer fazer para ele? 

Para cada um desses números, “for(int i =0; i <= THREADS; i++)”, para cada um deles o que eu quero fazer? 

Eu quero executar isso, aí você fala: “Guilherme, mas eu não preciso executar esse new toda vez.” 

Sim, eu não preciso, esse seria o meu provider.call, aqui eu teria o meu “provider” e eu teria o provider.call aqui para cada um deles.

Mas reparem, se é isso, é mais fácil chamar um ServiceProvider ”.start” e eu passo as “THREADS”, var provider eu posso tirar, esse eu posso tirar, criamos uma função start e é essa função start que vai startar tudo isso.

Só tem que tomar um cuidado aqui, se eu fizer isso eu vou ficar com a função call e a função start que vai lembrar a classe thread de Java para quem já trabalhou com Java, que tem duas funções e você sempre chama a errada, eu prefiro evitar isso.

Ao invés disso, isso daqui é o nosso serviço em si, então isso daqui é um “ServiceRunner”, Create ServiceRunner, o ServiceRunner vai receber o parâmetro que tem que receber, já descobrimos qual é e ele vai ter a função start, que vai startar as threads, vai ter um start que vamos fazer, “public void start”, que vai receber o “int threadCount” e vai fazer o threadCount para nós, para cada threadCount o que ele vai fazer? 

Ele vai fazer “pool.invokeAll”.

E eu posso passar diversos que eu quero que ele invoque e o que eu faço? 

“pool.submit” e eu passo ou um Runnable, ou um Callable, lembram, o Runnable não podia jogar exception mas o Callable pode jogar exception, eu vou chamar um submit com esse Callable, quem é o meu Callable? 

É o ServiceProvider.

Se eu recebi um provider, eu poderia receber aqui direto um “ServiceProvider” do tipo “t” e o que eu faço? 

Simplesmente uso esse provider, só que só um cuidado, como o ServiceProvider precisa da Factory conseguimos até esconder, jogamos ServiceFactory logo de cara e nós mesmos damos um “this.provider = new ServiceProvider(factory)”.

Quer dizer, eu criei isso aqui, esse é meu ServiceRunner, ele cria um provider, esse t eu não preciso e o que mais ele faz? 

Ele simplesmente submete esse “provider”, quer dizer que para a mesma instância desse objeto, ele vai chamar esse cara n vezes, threadCount vezes, é isso que ele faz, como ficou o nosso código final? 

Fica simplesmente new ServiceRunner para esse cara e starta quantas threads eu quero, são 5 que eu vou startar.

Eu fiquei com código super simples, eu só falo eu quero startar 5 e esses são os meus três métodos, com isso extraímos e escondemos todo esse nosso processo desses pequenos serviços, podemos rodar ele aqui.

Eu tenho um cuidado que eu tomaria de rodar ele aqui, mas vamos dar uma olhada, quando rodarmos ele, lembra que o nosso EmailService vai receber o mesmo ConsumerGroup para todos, mas e o ClienteId é diferente para cada um deles? 

Porque dentro de um ConsumerGroup eu tenho vários, devem ter 5 desses caras consumindo agora, será que eles tem id diferentes? 

É só darmos uma olhada no nosso código, o nosso ServiceRunner cria o ServiceProvider que usa o KafkaService e o KafkaService cada um tem um id diferente.

Vamos dar uma olhada, aqui eu deveria ter 5 caras consumindo se eu tenho 5 caras consumindo, cada um tem que estar com uma partição diferente e 2 sobrando, porque criamos só três partições, vamos ver, “bin/kafka-topics.sh”, bootstrap-server e describe, vamos ver os tópicos.

O tópico que ele está escutando é o de enviar e-mail, ECOMMERCE_SEND_EMAIL, tem 3, não era o topics, era o ConsumerGroups, describe, vamos ver, SEND_EMAIL, a partição 0, está com esse Consumer aqui, a partição 2 está com esse Consumer e a partição 1 está com esse Consumer.

Você fala: “Guilherme, mas e se der uma exception agora no nosso Consumer?” Imagina que consumimos e deu uma Dead letter, lembram do caso da Dead letter? 

No nosso KafkaService deu uma Dead letter só que na Dead letter deu uma exception, então quer dizer, deu uma exception dentro da exception, lembram que é um caso perigoso, porque aqui dentro deu outra exception e essa outra exception explodiu, é bem ruim porque isso explode daqui do nosso run, do nosso KafkaService.

O nosso KafkaService run está sendo chamado onde? 

No run, quer dizer que isso daqui vai explodir, se isso explodir vai ser onde mesmo? 

No nosso ServiceRunner? 

Não, ele submeteu para acontecer e ele deixa rodando, submeteu e deixou rodando, então está rodando o EmailService, se uma thread, você vai ver que o newFixedThreadPool, o que ele faz? 

A característica é, se alguma Thread terminar por causa de uma falha uma nova vai ser criada.

Se uma Thread estourar, vai ser criada uma nova thread, quer dizer, vai ficar com 5 threads de novo, só que, reparem, submetemos exatamente 5 vezes, se temos 5 threads e para cada uma nós começamos um desses clientes, acontece que, se uma cair não levantar automaticamente, só levanta a thread, o cliente não é submetido novamente.

O newFixedThreadPool só faz isso, ele não resubmete, ficaríamos com 5, caiu, foi para 4, caiu foi para 3, caiu foi para 2, caiu foi para 1, já era, você poderia colocar esquemas para detectar quando os serviços caem, levanta de novo, você poderia fazer aqui dentro do serviço você tem esse processo com 5 clientes rodando ou você poderia fazer por processos rodando na máquina, ou nas máquinas como ferramentas que ficam analisando essas máquinas.

Tem diversas maneiras de fazer isso, uma maneira simples é essa de rodar 5 em paralelo, da mesma maneira que fizemos com esse cara, podemos extrair todo esse código para o nosso CommonKafka, porque tudo isso está ligado com o Kafka de verdade e aplicar isso em outros serviços, eu vou querer aplicar em mais um serviço para vermos como ficará fácil a aplicação.

### Facilidade de criar novos serviços
Eu queria pegar agora um outro serviço nosso, o de reading-report, aquele que gera o relatório, eu queria paralisar ele, o que vamos fazer? 

Pegar aquelas classes que eu criei dentro do service-email, que é o ConsumerService, ServiceFactory, ServiceProvider e ServiceRunner, essas quatro que eu tenho aqui e jogar dentro do CommonKafka, src, main, java, estas eu quero mover daqui para Consumer, tudo isso é Consumer, Refactor, movi lá para dentro, só ter certeza que está ok.

ConsumerFunction, maravilha, ConsumerService, maravilha, o nosso de Email deveria rerodar novamente sem problemas, vamos conferir, vamos rodar ele de novo e se ele funcionar maravilha o que vamos fazer? 

Também vamos fazer isso, isto é, lembram como funciona? 

Implementamos ConsumerService.

Então vamos aqui, implementamos, “ConsumerService”. ConsumerService do quê? 

O que o ReadingReportService consome mesmo? 

User, se ele consome User, temos algumas funções para implementar, Implement Methods.

Eu não vou aproveitar esse parse porque ele já está implementado, ele só não é “public” por isso que está reclamando, agora que eu tenho os públicos, o tópico, qual é o tópico? 

É esse aqui, eu jogo esse tópico para cá, o que mais? 

O nosso ConsumerGroup, esse é o ConsumerGroup, aí você fala: “Você vai usar o ConsumerGroup sempre baseado em classe, podia retornar classe, ficava um pouco menos de código.” 

Divirta-se, é a estrutura que você vai definir para sua própria empresa.

A sacada é você ter uma camada que facilita todo esse trabalho para você, afinal se você tem que fazer isso 300 vezes, você não quer fazer tudo isso na mão 300 vezes, o que mais? 

Queremos ter também esse método main, ele é muito mais simples, ele é simplesmente isso daqui, ele simplesmente arranca tudo e ele usa ao invés do EmailService, “ReadingReportService::new”.

Está reclamando de alguma coisa, um não sobrescreve o outro, estou errando alguma coisa da sintaxe da interface, é ConsumerService, tem que ter um parse que recebe ConsumerRecord, coloquei string porque estava fixo, agora que é t é o usuário, maravilha, redondo, acabou.

Olha o código de novo como ficou, eu quero executar 5, executa coloca qual é o tópico que você quer escutar, coloca qual o nome do seu ConsumerGroup e o que você quer fazer em cada um desses cinco, é só fazer. 

Essa exception, vou adicionar para o nosso parse que antes não tinha, no nosso ConsumerService não tinha esse throws exception, deixa eu ver lá como está, se ficou alguém agora com possíveis exception, quando rodarmos descobriremos.

Rodamos agora ReadingReportService, vou escolher um módulo, é o do reading-report, ele vai reclamar se tiver alguém que precisa ter um try catch extra que eu não coloquei aí, vamos ver, está lá, é capaz de já ter cinco deles rolando, vamos ver.

Vou lá e dou um ConsumerGroups de novo, vamos ver os ConsumerGroups do tópico do Reading? 

No tópico do USER_GENERATE_READING_REPORT, Está assim, tem a partição 2 que é o cliente c4b, a partição 1 que o 89 e a partição 0 que é o 84, fica muito mais simples de começarmos a criar os nossos serviços agora.

Quer criar um serviço com suporte a Dead letter, com suporte a grande falha se não tiver o Dead letter, vai morrer aquele cara de vez, com suporte a multi thread, várias instâncias daquele serviço lá dentro? 

Implementa a função parse fazendo o que você quiser, implementa qual é o trópico, implementa qual é o teu nome, o nome do seu grupo, só isso, ficou bem mais simples.

Podemos dar um Build, Rebuild Project só para ter certeza de que está tudo buildando, não ficou nada sem compilar agora que fizemos algumas refatorações e maravilha.

### O que aprendemos?
* extraindo uma camada de interface de serviço
* paralelizando com thread pools
* simplicidade ao criar novos componentes

## commits e offsets
### Offset latest e earliest
Eu queria agora parar tudo que estamos fazendo, stop all, derrubar nossos servidores do Kafka, assim que tudo for derrubado, vou derrubar também o Zookeeper e remover todos os diretórios eu vou querer começar de uma coisa totalmente limpa, por quê? 

Porque eu quero dar uma olhada com mais calma o que acontece em determinadas situações, para isso, eu não vou precisar desses 5 rodando, só preciso parar tudo, parei, podemos dar uma olhada, lembram que as partições por padrão usam 3 réplicas.

O que eu posso fazer é primeiro apagar os diretórios "../data", o diretório que eu criei e apago tudo que está lá, eu vou começar um Zookeeper novo, vou começar um Kafka novo, ele começa rápido porque não tem muito o que recuperar, entender onde ele estava, etc, e começamos a começar os outros servidores, 3, 4, o 5.

O que eu quero dar uma olhada é o seguinte, vamos pegar o nosso servidor http, lembram o que ele faz? 

Ele tem uma requisição que cria uma nova compra, a requisição de nova compra gera um ECOMMERCE_NEW_ORDER e o EmailService o que ele faz mesmo? 

Escuta um ECOMMERCE_SEND, não é bem o que queríamos.

Quem é que faz a ponte entre uma nova compra e o EmailService? 

É o EmailNewOrderService, esse serviço é quem faz a ponte, este é um serviço que nós ainda não tinhamos migrado, eu tinha deixado quieto assim como outros serviços.

O que eu vou fazer é já mandar implementar agora o nosso ConsumerService, é do tipo Order, ele é público e vamos ter dois métodos para implementar, o que devolve essa linha de cima e o que devolve essa linha de cima aqui.

Vamos mandar implementar os dois métodos, implementa os métodos para mim, o tópico é esse, temos o EmailNewOrderService que é o SimpleName, que é o nosso ConsumerGroup e o que eu quero fazer é: eu vou instanciar este cara só uma instância, eu vou querer chamar o ServiceRunner só um. 

Você fala: “Você poderia usar herança, alguma coisa do gênero para não ter que ficar colocando essa linha.” Tem mil maneiras, eu não vou colocar essa, não gosto, não vou por esse caminho, vou para o caminho simples para mim.

Eu estou começando uma única Thread com esse EmailNewOrderService, o que ele faz mesmo? 

Ele escuta o tópico ECOMMERCE_NEW_ORDER e envia para o ECOMMERCE_SEND_EMAIL, o que eu vou querer fazer, eu quero dar uma olhada no que vai acontecer com esses caras, deixa só eu ver uma exception que está sendo jogada, essas exceptions tem que ser adicionada do nosso list e eu fechei a classe, fiz o que não deveria fazer, adicionei uma e adiciono a outra.

Tenho as exceptions lá, o que eu vou fazer é: eu gostaria de rodar esse cara e o http, eu gostaria de rodar os dois e ver o que acontece, vamos primeiro rodar a nossa Servlet, para pegar a Servlet eu tenho que rodar o HttpEcommerceService.

Eu vou rodá-lo aqui, lembram que esse Service não é um service Kafka, ele é um service de http portanto eu não estou usando o nosso CommonsKafka para startar vários serviços e etc, você poderia ter um CommonsHttp se você tiver vários servidores http, claro, tem que fazer o que faz sentido para você.

Vamos no nosso localhost, não é o generate-reports, o que queremos é o "/new", só que lembrem, o /new, que é a nossa Servlet, tem alguns parâmetros, tem o parâmetro email e o parâmetro amount", eu vou colocar um "email=guilherme@email.com" e "amount=500", quando eu chamar isso, deve enviar uma mensagem.

Vamos dar uma olhada antes quais são os ConsumerGroups, vamos dar uma olhada antes quais são os tópicos, as duas coisas, quais são os tópicos que existem até agora?

Nenhum, quais são os ConsumerGroups que existem até agora? 

Nenhum, ninguém está escutando.

O que eu vou fazer agora? 

Vou tentar enviar a mensagem, na hora que eu envio, o que acontece? 

O tópico é criado, por padrão o Kafka está configurado para criar o tópico automaticamente, eu poderia configurar para não fazer isso, já que criou, o ConsumerGroup existe? 

Não existe, ninguém está escutando, o tópico existe? 

Existe, com três partições, três réplicas, tudo bonito.

A questão é, quero levantar aquele serviço, o nosso serviço que tem uma única Thread, vou levantar ele, mandei rodar, vamos ver o que vai acontecer quando rodar, ele levanta, ele roda e no final de tudo, consumidor, startando consumidor, subscribe, etc, ele resetou o offset para 1.

Por quê? 

Porque ele falou, não encontrei nenhum offset comittado para a partição 1, não tinha encontrado offset afinal era um ConsumerGroup novo, se é um ConsumerGroup novo, ele começa onde? 

Da primeira mensagem mais antiga que eu tenho acesso ou de todas as mensagens a partir de agora? 

De onde ele deve começar? 

A resposta é sua, fica difícil, temos que decidir, vai ser de cara ou não vai ser de cara? 

Essas são configurações que temos dos nossos serviços.

Quando temos o ServiceRunner que cria o ServiceProvider, que usa o nosso KafkaService, o KafkaService, ele tem algumas propriedades, dentre essas propriedades, uma delas "properties.setProperty(ConsumerConfig.", algumas delas estão ligadas com o "OFFSET", a partir de onde eu devo começar? "OFFSET_RESET_CONFIG".

#### OFFSET_RESET_CONFIG
O que o OFFSET_RESET_CONFIG faz? 

O que fazer quando não tem um offset inicial, ou o offset não existe mais no servidor porque os dados foram apagados, imagina que nem agora, começamos um ConsumerGroup novo, não tem offset, não tem armazenado o quanto eu já consumi, mas eu tenho cinco mensagens lá, eu devo começar do zero ou eu devo começar do seis? 

**Por padrão ele está começando nessa propriedade, ele está começando do latest, do último**.

E você fala, vai ter situações que eu quero começar do mais antigo, é claro, se você começa do último o que acontece? 

É capaz de você perder mensagens se você não tem nada lá e se já tem mensagem lá, mas não tem nada lido de offset? 

Você pode perder mensagens e se você voltar para trás é capaz de ter acontecido o quê? 

É capaz de você não ter feito o commit direito e executar de novo essas mensagens.

Imagina que você recebeu elas, você estava processando elas, você não commitou ainda e caiu, você levantou de novo e você está processando desde o zero de novo, é um dos mil casos em que isso pode acontecer isso.

Não tem segredo, só tem decisões, o que eu devo fazer, o AUTO_COMMIT, o intervalo do AUTO_COMMIT, se você quer AUTO_COMMIT que dá para fazer manual, tudo detalhe, vamos ver essas coisas acontecendo, o AUTO_OFFSET, ao invés de deixarmos como está vamos configurar para “latest”, para o mais recente, AUTO_OFFSET_RESET_CONFIG, vamos lá na documentação, ConsumerConfig, AUTO_OFFSET.

O que você pode fazer? 

Você pode passar o valor smallest, o que é smallest, é o zero? 

Talvez, porque se você tiver um milhão de mensagem, algumas já foram apagadas por que eram muito antigas e você só tem as cem mil últimas, então o smallest vai ser as novecentas mil, porque é a última que está armazenada em disco que eu sou capaz de te entregar ainda, lembra, o Kafka por padrão não armazena eterno, pelo tempo que ele armazenou, o que ele ainda tem lá ele vai te definir o smallest que você consegue.

Largest quer dizer o maior de todos e disable quer dizer: “Para aí porque deu uma caca grande e eu não quero sair consumindo mensagem”. 

Para não correr o risco de consumir de novo, para não correr o risco de deixar de consumir eu simplesmente estou falando: “Não levanta, para porque eu não quero lidar com isso”, que é o disable, o largest significa o que já estávamos fazendo.

Se eu estou pedindo para ir pelo maior de todos, significa que se não tem informação do offset ele vai para o maior de todos, quer dizer, ele começa já no primeiro, o que eu posso fazer agora? 

Eu posso dar stop em tudo mundo e testar tudo de novo, podem ter outras maneiras de fazer isso, mas vamos fazer tudo do zero, por isso eu pedi para limparmos, vamos limpar tudo, sem dó, estou destruindo todos os nossos hubs, os nossos brokers, cinco brokers que vão embora e quando eles forem embora, eu vou matar também o meu Zookeeper.

Destruí o Zookeeper, por quê? 

Porque eu vou apagar todos os dados, começar o meu Zookeeper e começar tudo de novo, só antes de começar de novo, quero ter certeza que está tudo parado porque eu não quero ninguém comunicando, falando cadê o tópico tal, porque ele já vai criar o tópico tal e eu não quero que ele crie o tópico tal, eu quero fazer igual fizemos antes para vermos que o largest é exatamente o que tínhamos feito.

Sim, vamos fazer tudo isso de novo com um smallest também, comecei tudo, vou rodar o meu http, rodo ele, quando ele rodar, eu vou chamar a requisição que eu quero chamar, a mensagem vai ser enviada, eu vou rodar o nosso EmailNewOrderService que não deveria capturar essa mensagem de novo.

Por quê? 

Vou nos meus tópicos e no ConsumerGroups, temos os nossos tópicos, no ConsumerGroups ainda não tem ninguém consumindo porque o EmailNewOrderService não levantou direito, comecei ele e ele está falando, estou tentando, o Cluster Id é esse, eu encontrei o Producer 1 e 2, duas Thread de produção, encontrei a Thread do nosso pool de envio.

Terminei de levantar, eu posso conferir e é importante conferir que nenhum deles está falando de tópicos antigos, porque qualquer coisa que você deixou ali nos tópicos antigos ele ficará doido, porque vai ter mensagens que apagamos pela metade, aos tópicos, eu estou querendo um reset limpo porque o diretório novo de log do Kafka e do Zookeeper, porque eu quero que vejamos o primeiro caso específico quando um novo serviço entra no ar, como podemos lidar com essas situações, quando um novo ConsumerGroup entra no ar.

Eu vou rodar agora o nosso HttpEcommerceService, rodo ele, posso fazer a requisição, fiz a requisição, enviou a mensagem, podemos conferir os tópicos, o tópico está lá? 

Com três partições, diferente da última vez, o que eu posso fazer agora? 

Eu posso pedir para o nosso EmailNewOrderService rodar. 

O EmailNewOrderService, agora no consumo, está falando o quê? 

Para começar com o mais recente, o maior valor de todos, ele vai pedir o maior valor de todos, pediu e ele está rodando aqui.

Se eu tentar enviar uma nova mensagem o que acontece? 

A nova mensagem foi enviada? 

Foi enviada e agora vamos lá nos ConsumerGroups, esperamos, ele levanta e você vê lá, Producer está lá e você vê que ele trava aqui nos Producers, ele não está gerando um ConsumerGroup para nós, ele não chegou como antes e falou, o offset estava no tal e eu estou indo para o tal, que está acontecendo? 

#### AUTO_OFFSET
#### AUTO_OFFSET_RESET_CONFIG
O problema é essa configuração do AUTO_OFFSET.

Se formos lá no ConsumerConfig, o AUTO_OFFSET_RESET_CONFIG fala desses valores que temos que passar, o largest, smallest, disable, etc, só que qual é o problema? 

```diff
- O problema é que esse AUTO_OFFSET_RESET_CONFIG, ao invés de largest, ele é latest, Consume live messages in Kafka versão 2.3.0 da API.
```

Toma muito cuidado quando você vai acessar a API do Kafka, como teve uma mudança grande aqui, **você pode cair na versão antiga dele** que tem certas coisas que são diferentes, no **AUTO_OFFSET_RESET_CONFIG** está, essa é a versão 2 do Kafka, lembra que é a versão que nós estamos utilizando? 

"ConsumerConfig" e podemos procurar o "latest" e você vai achar as documentações, pessoas citando da utilização do latest e é ele te queremos utilizar, podemos olhar aqui que tem um exemplo, latest ao invés de largest, voltamos para cá, colocamos latest e restartamos o nosso EmailNewOrderService.

Toma cuidado com a versão documentação, como teve quebra de API e pior ainda, API é baseada em String em algumas situações nas implementações de Kafka em algumas linguagens, quando teve quebra, que foi de um major release, etc, a quebra é grande.

Agora ele fala, estou settando o offset para a posição tal, ele está indo agora para offset que é a nossa posição, vamos dar uma olhada no ConsumerGroup, eu enviei uma segunda mensagem, acredito que eu tenho duas mensagens, ele já está no CURRENT-OFFSET 2, é isso que ele está fazendo.

Você fala: “Mostra para mim que o latest está funcionando, mostra de novo, restarta de novo e mostra de novo.” 

Dá para roubarmos só para testar, lembra que é por ConsumerGroup? 

Se ao invés de eu decidir que eu defini isso, eu definir isso daqui, eu estou com um ConsumerGroup novo, basta eu rodar de novo que agora eu estou com um ConsumerGroup novo e consigo ver que ele já vai começar na segunda mensagem, porque eu fiz a minha requisição http duas vezes.

Vamos dar uma olhada, já vai estar no 2, nesse ConsumerGroup novo que é o 1EmailNewOrderService já está no 2, por quê? 

Porque ele já está lá para frente, esse é o latest, assim como vimos o latest tem também o "earliest", só para vermos, eu vou criar um ConsumerGroup temporário, vou rodar, se eu tenho duas mensagem sem disco que ainda não foram apagadas por causa do tempo, por causa do espaço e eu estou falando para pegar o earliest, o que ele vai fazer? 

Consumir as duas.

Vamos dar uma olhada? 

Olha ele falando para nós, não achei offsets, então eu estou zerando os offsets, porque você pediu o earliest, então eu encontrei a primeira mensagem, então eu encontrei a segunda mensagem e eu processei as duas mensagens.

O **latest** e o **earliest** são fundamentais para sabermos se queremos sair processando o que tinha no passado ou não quando temos um novo ConsumerGroup ou quando por algum motivo você perdeu o offset, por qualquer motivo você perdeu o offset, o latest e o earliest, o mais antigo ou mais recente servem para dizer isso.

Por que não mais o maior ou o menor? 

Porque o maior ou menor assumem que tem que ser numérico essa sequência dos offsets e não necessariamente precisariamos lidar com isso, ele pode implementar isso de outros algoritmos que não precisamos nos preocupar se ele quiser implementar e a coisa continuaria funcionando com o mais recente ou mais antigo, timestamp, seja lá o que for, ou algum id baseado em timestamp.

### O que aprendemos?
* como lidar com latest e earliest

## Lidando com mensagens duplicadas
### O problema da mensagem duplicada
Já vimos que com a configuração earliest ou latest na situação de um novo ConsumerGroup conseguimos decidir se queremos consumir lá para trás ou aqui para frente, já falamos também lá para trás sobre algumas características do Commit de uma mensagem, eu cheguei a comentar em alguns momentos, se procurarmos no “COMMIT” que ele é feito automaticamente de tanto em tanto tempo, você tem um intervalo de configuração aqui.

#### AUTO_COMMIT_INTERVAL_MS_CONFIG
Deixa-me ver se tem os valores, AUTO_COMMIT_INTERVAL_MS_CONFIG e esse commit de milissegundos, ativar ou desativá-lo significa que vamos fazer manual o Commit se quisermos, existem algumas situações em que o que pode acontecer? 

À medida que íamos fazer um Commit, mas ainda não fizemos o AUTO-COMMIT, porque ainda não passaram os segundos necessários o meu broker de mensagens tem certeza que eu ainda não consumi.

Por quê? 

Porque eu ainda não Commitei, mas se eu já fiz o processo da mensagem e ainda não pedi a próxima mensagem, não Commitei pelo AUTO-COMMIT e eu paro de funcionar, quando eu levanto de novo, o que acontece? 

Eu vou consumir uma mensagem novamente que eu já tinha processado.

E entramos em um problema que é o seguinte: 

podemos configurar os nossos serviços de certa maneira, os produtores e consumidores, em que não liguemos se perdermos mensagem, vimos isso com o acks, o acknowledgement, se não estamos preocupados, simplesmente ele vai em algum momento enviar a mensagem, nem precisa ser nesse instante e eu não estou nem aí, pronto, talvez eu perca mensagens, é uma abordagem.

#### ACKS_CONFIG
Vimos também um outro sistema em que queremos todo mundo em sync, o ACKS_CONFIG que vai falar para nós que queremos todo mundo, ainda junto com o ACXS, tem mais um detalhe, antes da terceira abordagem.

No ACKS ALL temos o mínimo de réplicas insync, quando estamos com o ACXS ALL, podemos falar o que quer dizer esse all na verdade, eu quero que seja duas insync, três insync, cinco insync, você pode configurar, ela dá até um exemplo aqui, um cenário típico seria um tópico com três réplicas e o min.insync réplica com dois, assim a maioria vai ter, mas como eu citei, tem empresas que usam o all, se são três réplicas, são três all como all mesmo.

Eu quero que seja replicado todo mundo, mesmo que isso me dê um lag maior, para eu ter certeza que a mensagem foi enviada, mas pelo menos eu tenho garantia que ela está escrita em três lugares, mesmo que dois caiam eu tenho como ler ela e por aí vai para frente.

Vimos isso, só que, qual é a abordagem do meio do caminho que ainda não resolvemos? 

Temos um caminho que garante que a mensagem seja entregue, um carinha que eu não estou nem aí se a mensagem vai ser entregue, mas do outro lado o que eu tenho? 

Se eu não estou nem aí, talvez eu receba ela uma vez, se eu estou preocupado, talvez ela receba uma vez, mas eu acabei de citar, talvez eu receba ela, não Commit, processe, não deu tempo de Commitar e eu receba ela de novo, isso é, eu recebo a mensagem duas vezes.

Tem um caso em que eu não ligo, quer dizer, eu posso receber 0 ou mais que eu não estou muito preocupado, tem o caso que é o que estamos discutindo por enquanto, que na verdade recebemos a mensagem uma ou mais vezes, podemos correr o risco de receber a mesma mensagem duas vezes, em situações bem extremas e se eu quiser receber exatamente uma vez, como eu posso fazer isso? 

É uma questão fundamental em muitos sistemas, como podemos tentar garantir isso? 

Vamos dar uma olhadinha.

## Kafka transacional
Eu comentei que tem cenários onde queremos certos tipos entregas de mensagens, falamos sobre o envio, mas eu queria saber agora sobre a entrega dessa mensagem para quem quer receber ela.

Existe um post que eu gosto bastante que simplesmente chama Kafka Transaction e a verdade é que: pelo menos uma vez, ou no máximo uma vez, são tipos de trabalho que podemos ter no Kafka com o que já fizemos, pelo menos uma vez será entregue essa mensagem, mas tem vezes em que gostaríamos que ela fosse entregue exatamente uma vez, para isso acontecer, ele dá três exemplos em que isso pode acontecer, quando pode acontecer, mas tem situações em que queremos isso.

Temos que configurar o nosso produtor e o nosso consumidor para serem capazes de entregar e receber somente uma vez e exatamente uma vez e existem diversas configurações para serem feitas exatamente para esse cenário.

#### TRANSACTIONAL_ID_CONFIG
Por exemplo, no produtor temos algumas configurações de produção, que seria meio que um copy e paste como fine tuning do que você precisar, algumas já falamos, por exemplo TRANSACTIONAL_ID_CONFIG não falamos, mas é um ID único para o seu produtor, é isso.

Ativar idempotência, é uma opção que tem que ser true, vamos falar o que é idempotência, ACXS_CONFIG tem que ser all que já falamos, RETRIES_CONFIG, quantas vezes ele vai retentar, tem que ser mais do que uma, porque tem que ser pelo menos uma? 

Tem que ser maior do que zero, talvez ele tenha errado no post, tem que ser maior ou igual a 1, ele falou maior do que um, mas acredito que é maior ou igual a 1.

#### MAX_IN_FLIGHT_REQUESTS
Quer dizer que se eu não consigo entregar, Tenta de novo, mas ele tinha aquele problema em que ele tenta entregar o pacote daqui a pouco e de agora ele se deu mal, então o MAX_IN_FLIGHT_REQUESTS tem que ser um, lembram que eu havia comentado disso lá atrás.

Essas são as configurações do Produtor e você consegue enviar as mensagens, você produz as mensagens e envia, na parte de enviar mensagem não tem muito segredo, é só você enviar, você pode dar um producer.commitTransaction manual.

E na hora de receber? 

Na hora de receber é uma trabalheira e temos algumas desvantagens, você vai ver que vai ser praticamente um trabalho de outra maneira quando possível, eu quero só mostrar justamente para que fujamos dessa quando possível, a outra vamos ver que é mais natural em muitas situações e aceitável.

O que fazemos? 

No cliente que vai consumir, fazemos algumas coisas, desativamos o AUTO_COMMIT, afinal se ele Commitar e eu ainda estou processando eu me dei muito mal, eu vou ter que desativar o AUTO_COMMIT, eu vou falar para ler do earliest e não do latest, quer dizer, eu estou correndo risco de ler duas vezes quando eu falei o earliest aqui e eu falo que o nível de isolamento é read_committed, vamos passar que ele escreve cada um de novo.

#### ENABLE_AUTO_COMMIT
ENABLE_AUTO_COMMIT é false porque vamos controlar o Commit do offset manualmente, 

#### AUTO_OFFSET_RESET_CONFIG
AUTO_OFFSET_RESET_CONFIG é earliest, quer dizer que não tem esse offset, nós que vamos lidar com esse primeiro offset de todos e o nível de isolamento é read_committed, quer dizer que somente mensagens Commitadas vão ser consumidas, commitadas significa no sentido de enviadas para o nosso líder e para as réplicas que forem necessárias.

#### ISOLATION_LEVEL
Eu poderia colocar um ISOLATION_LEVEL em que escreveu para um producer, imagina que eu sou um producer, escrevi em um broker, o producer que está esperando o acxs all ainda não recebeu a confirmação, mas se o ISOLATION_LEVEL é menor o que acontece? 

Alguém que quer consumir essa mensagem já pode consumir essa mensagem porque a mensagem já está lá, mesmo que ela não tenha sido replicada nas outras duas.

Reparem agora que quando os dois lados se conectam a complexidade fica maior, por quê? 

Porque quem produz quer um acknowledgement de 3, mas quem consome pode consumir quanto? 

Só um, ou tem que esperar estar replicado para consumir? 

Porque reparem, se eu consumir antes de estar replicado e de repente não consegue replicar, pode ser que o produtor envie novamente a mensagem, essa mensagem seja uma nova e eu consuma de novo, pode acontecer coisas do gênero.

Reparem que começa a cair em casos mais complexos, nessa situação queremos um ISOLATION_LEVEL que diz: “**Se o produtor está fazendo a transação dessas três réplicas, enquanto ele não terminar eu não leio, eu espero**.” 

Esse é o read_committed, só vai ler o que está Commitado no sentido de que foi enviado para as n réplicas.

Fazemos essa configuração, até aí só configuração e outras configurações a mais que você queira ter no seu consumidor e o código? 

Primeiro vamos dar um Subscribe. 

Subscribe nós sabemos fazer, só que ele já passa um parâmetro a mais que vamos ver daqui a pouco, que é para o rebalanceamento, só para o rebalanceamento vai ter um parâmetro a mais.

Damos um poll de algum tempo mínimo, para ver que estou com heartbeat, estou vivo, me dá, será que tem alguma coisa, só que o que é o ‘será que tem alguma coisa’? 

Pedimos para o nosso consumidor o assignment que são as partições, ele devolve para nós as partições, aqui estão nossa partições e o que queremos fazer? 

Quando temos essas partições, para cada uma dessas partições que eu estou responsável, imagina que eu estou responsável pelas partições 0, 3 e 17, eu tenho 50 partições, eu estou nas 0, 3 e 17.

Eu preciso saber em que ponto eu estou de cada uma dessas partições, imagina que eu estou dentro desse for na partição 0, onde eu estou? 

Lembram que o offset não está controlado mais pelo Kafka, está controlado por mim? 

Eu armazenei esse meu offset em algum lugar, em um banco de dados, em um arquivo em disco, sei lá onde.

Você lê o offset do seu banco, você fala para o seu banco de dados qual é o seu groupId, você fala qual é a partição do seu tópico e ele te devolve o seu offset, o que você faz? 

Você faz uma busca no consumidor para ele ir para esse offset, nessa partição vai para esse offset, cheguei nesse offset, agora que eu estou nesse offset, eu vou começar a consumir e ele começa a consumir.

Agora ela faz o consumer.seek para ir nessa posição, nessa partição desse tópico nesse offset e ele vai para o próximo topicPartition e faz a mesma coisa, o que ele está fazendo? 

Se eu dei três partições para você, você vai reposicionar seus cursores na posição da partição que você quer, não é bem posicionar o cursor, é só notificar, eu estou na 15, estou na 17, estou na 13 em cada uma dessas três partições, meio que isso que você está dizendo.

Você entra no laço real agora, você tinha um poll rápido só para você receber as suas partições, agora que você recebeu as suas partições você deu seek nelas e agora que você deu seek nelas o que você faz? 

Você começa a consumir, como você consome? 

Aquele for nosso normal, consome, processa com o que você tem que processar, faz o que você tem que fazer.

E você fala: “Eu queria enviar um e-mail.” Envia um e-mail. “Eu queria gravar em um banco de dados.” Grava no banco de dados e você salva no seu banco que o ID, para esse ConsumerGroup, para esse tópico, para essa partição é esse o offset, estou gravando local agora o meu offset.

Reparem que se você está trabalhando em um banco de dados, se o banco armazena esses dois valores, maravilha, porque você coloca esses dois valores na mesma transação do banco, isto é, você começa uma transação no seu banco, você está usando Datomic, está usando SQL, não importa, você começa uma transação, nessa transação você salva o quê? 

Os dados que você quer salvar e o teu offset novo.

Isto é, se a transação falhar, você não Commitou nem os dados nem o offset, você não precisou fazer um two fase commit em dois sistemas diferentes, no Kafka e no banco, está tudo no banco, tudo que você faz no banco agora, se você tem um offset no teu próprio banco, maravilha.

Para quem vai trabalhar com um serviço que tem o banco, dessa maneira você está garantido que, se você conseguir salvar no banco e conseguir salvar o seu offset no banco, maravilha, a sua transação está joinha.

Claro, se você enviou um e-mail e o serviço externo enviou o e-mail, salvar o offset no banco não vai adiantar da mesma maneira, não tem como, é aquele caso infeliz, você apertou o botão para lançar um foguete e o foguete já foi lançado, não dá para desapertar o botão você tem que ter outras estruturas para conseguir voltar atrás nesse tipo de coisa, mas você tem que se comunicar com uma coisa que não está mais na sua mão, um e-mail que não está mais na sua mão mais, que é diferente.

Reparem que essa abordagem significa o quê? 

Significa - ele vai explicando o código pedaço a pedaço - que você vai ser capaz de processar a mensagem uma única vez com sucesso, se falhar, talvez você processe de novo, tente processar de novo com sucesso, ou com falha, mas se deu sucesso, você só processou uma vez.

Isso pensando que onde você Commita o offset é o mesmo lugar que você o lê o offset, que é o mesmo lugar em que você está armazenando esses dados, esta é uma abordagem para quem está utilizando, por exemplo, o banco de dados.

Lembram que eu falei que você tinha que passar o subscribe em uma outra classe para rebalanceamento, está aqui a classe do rebalanceamento, você tem que redefinir os offsets do banco de dados, por quê? 

Porque se você vai rebalancear você tem que pegar os offsets que você estava e passar para as outras pessoas.

E as outras pessoas que estão passando os offsets vão ter que ler de alguma maneira esses novos offsets, de alguma maneira eles tem que fazer essa passagem, então aqui ele faz essa passagem de alguma maneira, não importa a maneira que você vai passar esses valores dentro do teu próprio banco.

Esse é um post “simples” que mostra como ficaria esse processo se eu quero garantir no meu banco que quando eu faço um insert qualquer, um select qualquer, um delete qualquer ou qualquer coisa que eu quero fazer no meu banco, eu processe aquilo uma única vez de uma maneira transacional, esse Kafka Transaction que ele chamou.

Existe uma outra abordagem para fazermos isso e eu quero mostrar a outra abordagem que é mais natural, literalmente no sentido natural da palavra e vem de bônus para nós em diversas situações.

#### Kafka Transactions (https://itnext.io/kafka-transaction-56f022af1b0c)
Kafka Transaction

Photo by Annie Spratt on Unsplash
For most cases from my experiences, at least-once or at most-once processing using Kafka was enough and allowed to process message events.

It is not easy to achieve transactional processing in Kafka, because it was not born for the transactional nature, I think.

In the next, let’s see why it is hard to get the whole transactional processing in an application using Kafka Streams which can do complex processing in your streaming application.

A kafka streams application can consist of many processing cycles of consum-process-produce. You can meet the following situations with your Kafka Streams application in many times:

If you use Kafka Streams in your application, there can be a lot of calls of the functions like map(), through(), transform(), flatMap(), etc , where repartitioning will occur, that is, new topics with intermediate topics will be created with new topic key.
If your kafka streams application is stateful, the local state will be synced to the changelog topic asynchronously.
If you have to save processing results to external DB in the middle of processing in your kafka streams application, you have to consider saving offsets to the same external DB too.
As seen in the above situations, there can be a lot of consuming, processing, producing processes with many different data storages like kafka, local storage, and external databases, where all the transactions have to be committed in atomic way. But it is really hard to achieve the whole transactions commit in the atomic manner in your kafka streams application.

Rather, it should be divided to the individual consume-process-produce cycle.

Now, I will show you how to do producer-side transaction and consumer-side transaction to accomplish exactly-once processing in Kafka:

In producer-side transaction, kafka producer sends avro messages with transactional configuration using kafka transaction api.
In consumer-side transaction, kafka consumer consumes avro messages from the topic, processes them, save processed results to the external db where the offsets are also saved to the same external db, and finally all the db transactions will be commited in the atomic way.
The complete codes for this article can be found in my github repo:

mykidong/kafka-transaction-example
You can't perform that action at this time. You signed in with another tab or window. You signed out in another tab or…
github.com

Transactional Kafka Producer
Let’s first discuss producer-side transaction in kafka.

The following transactional properties for the producer can be set:

        // transaction properties.
        props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, transactionalId); // unique transactional id.
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 3);
       props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1);
        props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 600000);
ProducerConfig.TRANSACTIONAL_ID_CONFIG must be unique transactional id for the individual kafka producer.
ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG must be true.
ProducerConfig.ACKS_CONFIG is all , that is, all the leader and follower brokers have committed messages to the log.
ProducerConfig.RETRIES_CONFIG should be larger than 1 to try to request many times falls the request failed.
ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION is 1, it means ordered sequence will be guaranteed.
Let’s see the messages sent by kafka producer transactionally:

// construct producer.
KafkaProducer<UserKey, Events> producer = new KafkaProducer<>(props);

// initiate transaction.
producer.initTransactions();
log.info("tx init...");
try {
    // begin transaction.
    producer.beginTransaction();
    log.info("tx begun...");

    for(int i = 0; i < 20; i++) {
        Events events = new Events();
        events.setCustomerId("customer-id-" + (i % 5));
        events.setOrderInfo("some order info " + new Date().toString() + "-" + i);

        Date date = new Date();
        events.setEventTime(date.getTime());


        UserKey key = new UserKey(events.getCustomerId().toString(), date);


        // send messages.
        Future<RecordMetadata> response = producer.send(new ProducerRecord<UserKey, Events>(topic, key, events));
        log.info("message sent ... " + new Date().toString() + "-" + i);

        RecordMetadata recordMetadata = response.get();
        log.info("response - topic [{}], partition [{}], offset [{}]", Arrays.asList(recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset()).toArray());
    }

    // commit transaction.
    producer.commitTransaction();
    log.info("tx committed...");

} catch (KafkaException e) {
    // For all other exceptions, just abort the transaction and try again.
    producer.abortTransaction();
}

// close producer.
producer.close();
Before sending messages, Kafka producer Transaction has to be initialized: producer.initTransactions()
After sending messages, commit transaction: producer.commitTransaction()
If exceptions occurred, abort transaction: producer.abortTransaction()
Transactional Kafka Consumer
Now, let’s see the consumer-side transaction.

In my scenario, you have to consume, process messages, and save the processed results to the external database. At the same time, the offset also has to be saved to the same external database.

Before moving to consuming messages, let’s see the db table offset schema which can be found in the above git repo:

CREATE TABLE `offset` (
    `group_id` VARCHAR(255),
   `topic` VARCHAR(255),
   `partition` INT,
   `offset` BIGINT,
   PRIMARY KEY (`group_id`, `topic`, `partition`)
);
This is offset table which the offsets will be saved onto and retrieved from for the individual topic partition of the consumer group.

To consume messages transactionally, the following configuration can be set to the consumer:

        // transaction properties.
        props.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
ConsumerConfig.ISOLATION_LEVEL_CONFIG must be read_committed , it means that only committed messages will be consumed by consumer. ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG must be false , which means that the consumer will control the offset commit manually.
ConsumerConfig.AUTO_OFFSET_RESET_CONFIG should be earliest , it means if there is no offset commit for the partition consumed by the consumer with the specified consumer group id, the consumer will consume the messages from the first offset in the partition.
Now, kafka consumer will consume messages with these transactional configurations:

try {
    // consumer subscribe with consumer rebalance listener.
    consumer.subscribe(Arrays.asList(topic), new TransactionalConsumerRebalanceListener(this));
    consumer.poll(0);

    // When the consumer first starts, after we subscribed to topics, we call poll()
    // once to make sure we join a consumer group and get assigned partitions and
    // then we immediately seek() to the correct offset in the partitions we are assigned
    // to. Keep in mind that seek() only updates the position we are consuming from,
    // so the next poll() will fetch the right messages.
    for (TopicPartition topicPartition : this.consumer.assignment()) {
        long offset = getOffsetFromDB(groupId, topicPartition);
        consumer.seek(topicPartition, offset);
        log.info("consumer seek to the offset [{}] with groupId [{}], topic [{}] and parition [{}]", Arrays.asList(offset, groupId, topicPartition.topic(), topicPartition.partition()).toArray());
    }


    while (true) {
        // if wakeupCalled flag set to true, throw WakeupException to exit, before that flushing message by producer
        // and offsets committed by consumer will occur.
        if (this.wakeupCalled) {
            throw new WakeupException();
        }

        ConsumerRecords<String, Events> records = consumer.poll(100);
        if(!records.isEmpty()) {
            for (ConsumerRecord<String, Events> record : records) {
                String key = record.key();
                Events events = record.value();

                log.info("key: [" + key + "], events: [" + events.toString() + "], topic: [" + record.topic() + "], partition: [" + record.partition() + "], offset: [" + record.offset() + "]");

                // process events.
                processEvents(events);

                // an action involved in this db transaction.

                // NOTE: if consumers run with difference group id, avoid saving duplicated events to db.
                saveEventsToDB(events);

                // another action involved in this db transaction.
                saveOffsetsToDB(groupId, record.topic(), record.partition(), record.offset());
            }

            commitDBTransaction();
        }
    }

} catch (WakeupException e) {

} finally {
    commitDBTransaction();
    this.consumer.close();
}
The consumer will subscribe the messages from the topic with consumer rebalance listener:

consumer.subscribe(Arrays.asList(topic), new TransactionalConsumerRebalanceListener(this));
In TransactionalConsumerRebalanceListener consumer rebalance listener, the db transactions will be committed before the rebalancing is started, and consumer will seek to the offset from the database after the consumer is assigned to the specific partition:

public class TransactionalConsumerRebalanceListener<K, V> implements ConsumerRebalanceListener {

    private static Logger log = LoggerFactory.getLogger(TransactionalConsumerRebalanceListener.class);

    private AbstractConsumerHandler<K, V> consumeHandler;

    public TransactionalConsumerRebalanceListener(AbstractConsumerHandler<K, V> consumeHandler)
    {
        this.consumeHandler = consumeHandler;
    }

    @Override
    public void onPartitionsRevoked(Collection<TopicPartition> collection) {
        // commit db transaction for saving records and offsets to db.
        this.consumeHandler.commitDBTransaction();
    }

    @Override
    public void onPartitionsAssigned(Collection<TopicPartition> topicPartitions) {
        for(TopicPartition topicPartition : topicPartitions)
        {
            // get offset from db and let consumer seek to this offset.
            String groupId = this.consumeHandler.groupId;
            long offset = this.consumeHandler.getOffsetFromDB(groupId, topicPartition);
            this.consumeHandler.getConsumer().seek(topicPartition, offset);

            log.info("in rebalance listener, consumer seek to the offset [{}] with groupId [{}], topic [{}] and parition [{}]", Arrays.asList(offset, groupId, topicPartition.topic(), topicPartition.partition()).toArray());
        }
    }
}
When the consumer is restarted, the consumer will look up the last updated offset from the database for the partition:

long offset = getOffsetFromDB(groupId, topicPartition);
consumer.seek(topicPartition, offset);
Take a look at the saveEventsToDB() and saveOffsetsToDB() where the processed results and offsets will be saved to the same external database. Finally, all the db transactions will be committed with commitDBTransaction().

Exactly-once processing is not easy in Kafka, but as mentioned in this article, you should consider producer- and consumer-side transaction seperately, and try to achieve transactional processing.

You can see the following wiki pages about the details to run the codes in this article from my github repo:

https://github.com/mykidong/kafka-transaction-example/wiki/Run-Transactional-Kafka-Producer-and-Consumers
https://github.com/mykidong/kafka-transaction-example/wiki/Scenario