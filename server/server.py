# server.py
# coding: utf-8

import socket
import json

# Configuração da conexão
serverPort = 16000
serverSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
serverSocket.bind(('', serverPort))
serverSocket.listen(0)

print("Servidor pronto para receber")

# Conta o número de alunos matriculados em uma turma
def getNumeroMatriculados(alunos):
    numero = 0
    for i in alunos:
        if(i['matriculado'] == True):
            numero +=1
    return numero

# Loop que aguarda a conexão do cliente e processa a mensagem recebida
while True:
    try:
        connectionSocket, addr = serverSocket.accept()
        print("Conexão vinda de {}".format(addr))
        message = connectionSocket.recv(2048)
        decodedMessage = message.decode('utf-8')
        print("{} ==> {}".format(addr, decodedMessage))
        # Transforma o objeto recebito em um objeto JSON
        turmasJSON = json.loads(decodedMessage)
        modifiedMessage = ""
        # Gera uma mensagem que será retornada ao cliente
        for turma in turmasJSON["turmas"]:
            modifiedMessage += (f"A turma {turma['curso']} de {turma['ano']} possui {len(turma['alunos'])}"
                                f" alunos, dos quais {getNumeroMatriculados(turma['alunos'])} estão devidamente matriculados.#")
        

        # Retorna a mensagem modificada
        connectionSocket.send(modifiedMessage.encode('utf-8'))

        # Encerra a conexão com o cliente
        connectionSocket.close()
    except ConnectionResetError:
        print(f"Conexão perdida com {addr}")

