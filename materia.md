# Kafka
iniciado em 18/08/2022

terminado em 

[certificate]() 

- [Kafka](#kafka)
- [Kafka: Produtores, Consumidores e streams](#kafka-produtores-consumidores-e-streams)
  - [Produtores e consumidores](#produtores-e-consumidores)
    - [Mensageria e Kafka](#mensageria-e-kafka)
    - [Instalando o Kafka localmente](#instalando-o-kafka-localmente)


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

Então, eu estou dentro do diretório anterior, vou entrar no diretório apps(espaço)descompactadas, de proposito, tar zxf. 

No diretório meu de download, eu tenho um kafka, descompactei entro no diretório do Kafka, estou dentro do diretório do Kafka.

Aqui dentro, você vai ver que a gente tem vários diretórios, o de scripts e o de configurações. 

Então, eu quero rodar, o diretório dentro do diretório de scripts, kafka, server, start, levando o servidor, com a configuração padrão de servidor.

E aí, eu tento rodar e ele dá um monte de erro, o erro, parece que o projeto não está construído, mas não é isso, lembrem, a gente baixou a versão, a versão que eu baixei é a versão binária, com o projeto já construído, já está binário, já da para executar, não foi o código fonte que eu baixei, não baixei o código fonte, eu baixei a binária.

Então, qual que é o problema? O problema está aqui antes, o diretório path que eu estou usando, no meu path, apps descompactadas tem um espaço e aí, nesse espaço, o kafka fica doido. Então, o que que a gente vai ter que fazer? A gente vai ter que renomear esse diretório aqui, para simplesmente apps.

Então, eu vou voltar aqui para trás, só para eu não me perder, estou no diretório anterior, eu vou renomear isso aqui só para apps, vou entrar nessas apps, agora sim, dentro do kafka e agora sim eu posso rodar. Então, eu vou tentar bin/kafka-server-start, configurações, server properties.

Tento executar, ele roda o meu Java, então o meu Java já está instalado, eu estou com o Java 13, instala a versão mais recente que você tem, se você não tem Java instalado ainda, só que ele começa a dar vários erros. Ele fala: “Erro, erro, erro, não consegui, estou desligando” e ele desliga, por quê?

Porque o Kafka, ele é o processador dessas mensagens, de jogar de um lado para cá, para o outro lado, de cá, para lá, não sei o quê, não sei o quê, que lida com todos os strings e etc., processador nesse sentido de conectar tudo, mas onde que armazena essas informações?

Algumas informações básicas, o Kafka tem que armazenar em algum lugar e o lugar onde o Kafka armazena, isso por padrão, se chama zookeeper. Então, a gente teria que baixar também o zookeeper, zookeeper download. Então, a gente teria que baixar o zookeeper.

Claro, o Kafka já vem com o zookeeper instalado, caso você não queria instalar separadamente, porque pode ter empresas que já tenham o zookeeper rodando por outros motivos. No nosso caso, a gente não tem, então o que que eu vou fazer?

Antes de rodar o Kafka, eu vou limpar a minha tela e vou executar bin/zoopeeper, adivinha? Server-start e adivinha, preciso de configuração, config/zookeeper.properties. Então, eu já tenho as propriedades padrão aqui, já configuradas, vou utilizar elas.

Então, ele vai levantar para mim, olha, aqui embaixo, vou dar uns “Enter” para ficar mais rápidos, mais fácil de ler, conectei com o 0000, aqui, qualquer lugar para conectar, etc., na porta 2181. Então, o zookeeper está rodando, agora, sim, vou abrir uma outra aba, mesmo diretório, agora, sim, bin/kafka-server-start, e lembra as configurações?

Config, server.properties, vou tentar e aí, ele vai tentar conectar com o Zookeeper, e ele fala lá: “Conectei com o zookeeper”, manda um monte de propriedades padrão que ele está utilizando, etc., e no final, você vai ver que olha: “Started”, no final vai estar “Started”.

Então, ele está rodando o Kafka em algum canto para a gente, o Kafka está rodando lá. A gente pode até procurar aqui ó, porta, 9092, a porta padrão 9092, que vem no server properties, está escrito no server properties.

Então, a gente tem a propriedade que está na porta 9092, rodando o Kafka, por trás, usou keeper para algumas configurações, não todos os dados, algumas configurações. Vamos então agora, enviar uma mensagem de um lado para o outro. Eu quero ver o Kafka rodando, vamos testar?

Então, a gente vai testar um terminal de novo no mesmo diretório, Kafka, lembra, a gente vai usar várias coisas a partir daqui e o que eu vou querer fazer é criar um tópico, para a gente poder trocar mensagens. Então, bin/kafka tópicos, e aí, eu posso ver tudo o que o kafka topics permite que eu faça, tem várias coisas.

O que a gente vai fazer é um create, criar um tópico, só que a gente precisa falar aonde que está o Kafka rodando, lembra? O Kafka está rodando, bootstrap-server em localhost, porta 9092. Então, conecta com o Kafka localhost 9092. E aí, eu falo alguma propriedades do meu tópico.

Eu vou falar, colocar duas propriedades padrão aqui simples, que a gente vai trabalhar com elas durante todos os cursos, então não se preocupe, a gente vai entrar afundo nelas e brincar com elas, ver o que acontece com elas aos poucos. Por enquanto, eu só queria deixar fixo, replication factor como 1 e o partitions como 1.

E eu vou falar o nome do tópico, o nome do tópico, você pode botar o padrão que você quiser, imagine que eu tenho na minha loja um novo pedido que está entrando, então eu poderia colocar loja_novopedido. Aí, você fala: “Ah, Guilherme, não poderia ser loja, ponto, underline aqui?”.

Poderia, a sugestão do kafka topics é que a gente não use, não misture ponto com underline, por isso eu não vou misturar. Eu vou manter qual padrão? Eu vou manter aqui o underline como uma separação de meio que “subtópicos”, não são subtópicos, não existe esse conceito, é só uma separação para leitura.

Olha, loja teve um novo pedido, então a loja teve um novo pedido, só que você fica com a sensação, será que é o melhor padrão? Não existe muito um melhor, pior padrão. Existe que ponto e underline não é uma recomendação de uso ao mesmo tempo pelo próprio Kafka.

Então, vou utilizar sempre o underline para separar as palavras aqui para mim, é isso que eu vou utilizar, estou feliz com esse padrão, tudo em maiúsculo, você poderia definir que é tudo em minúsculo, poderia definir o que você quisesse como padrão.

No meu caso, eu vou utilizar esse e a sua empresa vai utilizar um padrão próprio, não tem problema. Então, vamos mandar criar esse tópico e quando a gente manda criar, ele fala aqui: “De acordo com limitações nas métricas dos nomes, tópicos com período, ponto ou underscore podem colidir.

Então, se você tem underscore, pode colidir, então a melhor maneira é usar ou um ou outro, mas não ambos e a gente não está usando ambos, está usando só um. O problema então, seria se a gente usasse um ponto em alguns, underline em outros, poderia ter uma colisão.

Estou usando só underline, não tenho problema, você poderia usar outros caracteres, como o hífen e por aí, vai. Estou usando underline, padrão que você definir de acordo com o que você definir para a sua empresa. Quero ter certeza que esse tópico foi criado, kafka-topics, menos, menos, list, de novo, eu tenho que falar qual é o servidor, bootstrap-server localhost 9092.

Então, esses comandinhos são super úteis no dia-a-dia a gente vai usar várias vezes. Então, ele está falando: “Existe, sim, um tópico chamado: loja novo pedido”. Queria mandar algumas mensagens para esse tópico, só para a gente ver isso acontecendo, envio de mensagens, porque a gente vê no kafka, ele está falando: “Eu criei a partição para esse tópico”.

E está lá, partição 0, só existe uma partição, começa com 0 e por aí, vai. Então, a gente vê aí que o tópico realmente está lá. O que eu quero fazer agora é falar para ele: “Olha, eu quero...”, vamos deixar essa aba aqui aberta, que é dos tópicos, que a gente vai trabalhar nos tópicos, vamos abrir uma outra aba.

Eu quero rodar um produtor de mensagens, que produz mensagens, de novo, no terminal, porque a gente só quer confirmar a instalação, tudo ok, kafka, eu quero criar no console um produtor e eu vou falar para ele assim: “Olha, os brokers, os kafkas estão rodando aonde mesmo?”.

Onde que eles estão rodando? Broker list, no localhost 9092 e eu falo o tópico, qual que é o tópico mesmo? Loja novo pedido. Quando eu rodo na linha de comando o console producer, cada linhazinha que eu digitar aqui é uma mensagem.

Então, eu poderia falar: “Olha, o pedido 0 com vírgula”, sei lá o valor foi R$ 560,00 e depois o pedido 1, o valor foi R$ 330,00, depois o pedido 2, que foi esse número aqui grandão. Então, estou mandando várias mensagens para esse tópico, a gente pode dar o list no nosso topics, para ver os tópicos que existem e o tópico loja novo pedido ainda existe.

O que eu quero fazer agora é consumir. Então, eu vou abrir uma nova aba, bin/kafka-console-consumir, bootstrap-server, então eu vou conectar bootstrap-server no localhost 9092 e o tópico loja novo pedido. Agora, vem uma pergunta, eu devo consumir a partir de quando?

Desde lá de trás? Desde a primeira mensagem que está armazenada? Ou a partir das mensagem que chegam agora? Se eu executo do jeito que está, do jeito que está. Não veio mensagem nenhuma aqui, agora, se eu... Vou abrir uma aba nova, se eu executar bin/kafka-console-consumer, bootstrap-server, lembra que eu estou deixando várias abas abertas?

Para que a gente possa usar o para cima e não ter que ficar digitando isso aqui toda a vez, 9092, o topic loja novo pedido, mas eu poderia falar: “Olha, começa do começo”, falar um beginning, então ele vai começar da primeira mensagem que está armazenada ainda no Kafka.

Então, ele vai olhar lá para trás e vai falar: “Olha, de qual a mensagem que está armazenada?”, está armazenada essa mensagem, essa mensagem e essa mensagem. Então, aqui ele ainda não recebeu nada, porque não teve novas mensagens e aqui sim, aqui a gente teve três mensagens do passado, que estavam armazenadas esperando alguém consumir.

Então, vamos ver agora, vou mandar uma mensagem um pedido 3 com esse outro valor, mandei. Então, olha, aqui eu recebi o pedido 3 e aqui eu recebi o pedido 3, recebi nos dois consumidores.

É claro, a gente vai ver se a gente quer receber em todos os consumidores, em só um consumidor, como receber, quantas partições, quantas repetições, ter certeza que vai receber, certeza que começou desde o começado, várias coisas que a gente vai discutir durante o curso.

Mas isso daqui é legal para a gente ver como instalar o kafka, como olhar os tópicos que estão lá, a gente vai explorar o topics mais vezes... como gerar um consumidor, um produtor de strings, que manda strings simples e um consumidor ou mais de um consumidor, que consome essas strings, só para gente ver funcionando.

Claro, a partir de agora, a gente quer executar isso com programação e ver todas as vantagens e desvantagens que a gente vai ver com o kafka dentro dos nossos programas. São os nossos próximos passos.