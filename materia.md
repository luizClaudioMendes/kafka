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

