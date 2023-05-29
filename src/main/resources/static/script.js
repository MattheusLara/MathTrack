document.getElementById('generateReport').addEventListener('click', function() {
    const criancaUuid = document.getElementById('criancaUuid').value;
    const plataforma = document.getElementById('plataforma').value;
    const errorMessageElement = document.getElementById('errorMessage');
    
    // Limpar qualquer mensagem de erro anterior
    errorMessageElement.style.display = 'none';
    errorMessageElement.textContent = '';

    if (!criancaUuid || !plataforma) {
        errorMessageElement.textContent = 'Por favor, preencha todos os campos. A UUID da criança deve ser o identificador único da criança. A plataforma deve ser mobile ou realidade virtual.';
        errorMessageElement.style.display = 'block';
        return;
    }

    fetch('http://localhost:8080/gerar-relatorio-jogo/geral', {
        method: 'POST', 
        headers: {
            'criancaUuid': criancaUuid,
            'plataforma': plataforma
        }
    })
    .then(response => {
        if (response.headers.get('Content-Type') === 'application/pdf') {
            response.blob().then(blob => {
                const url = URL.createObjectURL(blob);
                const link = document.createElement('a');
                link.href = url;
                
                const now = new Date();
                const dateTimeString = now.toLocaleDateString() + " " + now.toLocaleTimeString();

                const linkText = document.createElement('span');
                linkText.textContent = `Relatório para a criança ${criancaUuid} na plataforma ${plataforma} gerado em ${dateTimeString}.`;

                const downloadIcon = document.createElement('i');
                downloadIcon.classList.add('fas', 'fa-cloud-download-alt');
                downloadIcon.style.marginLeft = '5px';
                downloadIcon.style.marginRight = '10px';

                link.appendChild(linkText);
                link.appendChild(downloadIcon);

                link.target = '_blank';

                document.getElementById('pdfResult').appendChild(link);
                document.getElementById('pdfResult').style.display = "block";
            });
        } else {
            response.json().then(error => {
                console.error('Erro:', error);
                errorMessageElement.textContent = 'Não foi possível gerar o relatório. ' + (error.message ? 'Detalhes: ' + error.message : '');
                errorMessageElement.style.display = 'block';
            });
        }
    })
    .catch((error) => {
        console.error('Erro:', error);
        errorMessageElement.textContent = 'Ocorreu um erro ao gerar o relatório. Por favor, tente novamente mais tarde. Se o problema persistir, entre em contato com o suporte técnico.';
        errorMessageElement.style.display = 'block';
    });
});