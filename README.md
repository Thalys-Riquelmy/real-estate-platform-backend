# DOCUMENTO DE ESPECIFICAÇÃO DO MVP - SISTEMA IMOBILIÁRIO (COMPLETO)

## Versão: 1.0.0
## Data: 10/04/2026

---

## 1. VISÃO GERAL DO PROJETO

### 1.1 Nome do Projeto
**imobiliaria-api** - Sistema de Gestão Imobiliária (Backend)

### 1.2 Descrição
API REST para gestão completa de uma imobiliária, incluindo controle de:
- Empresas (multi-empresa)
- Usuários e autenticação
- Proprietários de imóveis
- Clientes (compradores/locatários)
- Imóveis com upload de fotos
- Contratos de venda financiada (com IGP-M e juros)
- Contratos de aluguel
- Prestações e pagamentos
- Envio de emails por empresa
- Dashboard e relatórios

### 1.3 Objetivo do MVP
Entregar um sistema funcional para **um único cliente** (imobiliária) com capacidade de expansão para multi-empresa no futuro.

---

## 2. TECNOLOGIAS UTILIZADAS

### 2.1 Backend
| Tecnologia | Versão | Finalidade |
|------------|--------|------------|
| Java | 21 | Linguagem principal |
| Spring Boot | 3.2.4 | Framework principal |
| Spring Data JPA | - | Persistência de dados |
| Spring Security | - | Autenticação e autorização |
| Spring Validation | - | Validação de dados |
| Spring Mail | - | Envio de emails |
| Spring AOP | - | Programação orientada a aspectos |
| SpringDoc OpenAPI | 2.5.0 | Documentação da API (Swagger) |
| JJWT | 0.11.5 | Geração de tokens JWT |
| Lombok | 1.18.32 | Redução de código boilerplate |
| MapStruct | 1.5.5.Final | Mapeamento Entity ↔ DTO |
| PostgreSQL | 42.7.3 | Banco de dados relacional |
| Maven | - | Gerenciador de dependências |

### 2.2 Banco de Dados
- **SGBD:** PostgreSQL
- **Database:** imobiliaria_db
- **Porta:** 5432
- **Credenciais:** postgres / ATrick24

### 2.3 Ambiente de Desenvolvimento
- **IDE:** Eclipse / IntelliJ
- **Porta da aplicação:** 8080
- **Context Path:** /api

---

## 3. ARQUITETURA DO SISTEMA

### 3.1 Arquitetura Utilizada
**Arquitetura MVC (Model-View-Controller) tradicional com camadas bem definidas.**

```
┌─────────────────────────────────────────────────────────────────────────┐
│                         ARQUITETURA MVC                                 │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  ┌─────────────────────────────────────────────────────────────────┐    │
│  │                      CONTROLLER                                  │    │
│  │  - Recebe requisições HTTP                                       │    │
│  │  - Valida dados de entrada                                       │    │
│  │  - Chama os Services                                             │    │
│  │  - Retorna respostas HTTP                                        │    │
│  └─────────────────────────────────────────────────────────────────┘    │
│                                    │                                     │
│                                    ▼                                     │
│  ┌─────────────────────────────────────────────────────────────────┐    │
│  │                       SERVICE                                    │    │
│  │  - Regras de negócio                                             │    │
│  │  - Validações específicas                                        │    │
│  │  - Cálculos (juros, IGP-M, parcelas)                             │    │
│  │  - Chama os Repositories                                         │    │
│  └─────────────────────────────────────────────────────────────────┘    │
│                                    │                                     │
│                                    ▼                                     │
│  ┌─────────────────────────────────────────────────────────────────┐    │
│  │                      REPOSITORY                                  │    │
│  │  - Acesso ao banco de dados (JPA)                               │    │
│  │  - Queries personalizadas                                        │    │
│  └─────────────────────────────────────────────────────────────────┘    │
│                                    │                                     │
│                                    ▼                                     │
│  ┌─────────────────────────────────────────────────────────────────┐    │
│  │                         MODEL                                    │    │
│  │  - Entidades JPA                                                 │    │
│  │  - Mapeamento das tabelas                                        │    │
│  └─────────────────────────────────────────────────────────────────┘    │
│                                                                          │
│  ┌─────────────────────────────────────────────────────────────────┐    │
│  │                         DTO                                      │    │
│  │  - Data Transfer Objects                                         │    │
│  │  - request/ (dados de entrada)                                   │    │
│  │  - response/ (dados de saída)                                    │    │
│  └─────────────────────────────────────────────────────────────────┘    │
│                                                                          │
│  ┌─────────────────────────────────────────────────────────────────┐    │
│  │                         MAPPER                                   │    │
│  │  - Conversão entre Entity ↔ DTO                                  │    │
│  └─────────────────────────────────────────────────────────────────┘    │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## 4. O QUE JÁ FOI IMPLEMENTADO (MÓDULO 1 E 2)

### 4.1 Módulo 1: Configuração Base
✅ Estrutura do projeto criada  
✅ Dependências configuradas (pom.xml)  
✅ application.properties configurado  
✅ Banco de dados PostgreSQL configurado  
✅ Tabelas criadas automaticamente (Hibernate DDL auto)

### 4.2 Módulo 2: Autenticação e Empresa
✅ **Empresa** - CRUD completo
✅ **Usuário** - CRUD completo
✅ **Configuração de Email** - Por empresa
✅ **Configuração Financeira** - Por empresa
✅ **Autenticação JWT**
✅ **Tratamento de Exceções**
✅ **Documentação Swagger**

---

## 5. PRÓXIMOS PASSOS PARA CONCLUIR O MVP

### 5.1 Ordem de Desenvolvimento Recomendada

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    PRÓXIMOS MÓDULOS (ORDEM PRIORITÁRIA)                 │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  ✅ MÓDULO 1: Configuração Base - CONCLUÍDO                             │
│  ✅ MÓDULO 2: Autenticação e Empresa - CONCLUÍDO                        │
│                                                                          │
│  ⬜ MÓDULO 3: Proprietários - PRÓXIMO                                   │
│  ⬜ MÓDULO 4: Clientes                                                   │
│  ⬜ MÓDULO 5: Imóveis (com upload de fotos)                             │
│  ⬜ MÓDULO 6: Contratos de Venda (Financiamento)                        │
│  ⬜ MÓDULO 7: Contratos de Aluguel                                      │
│  ⬜ MÓDULO 8: Financeiro (Parcelas, Pagamentos, Juros, Carnê)          │
│  ⬜ MÓDULO 9: Envio de Emails (Configuração por empresa)               │
│  ⬜ MÓDULO 10: Dashboard e Relatórios                                   │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

(Veja o documento da task para os detalhes exatos de cada end-point e módulo, incluindo a correção do IGP-M)

---

## 6. REQUISITOS OBRIGATÓRIOS PARA MVP (CONTINUAÇÃO)

### Requisitos Funcionais (Completos)

| ID | Requisito | Prioridade | Módulo | Status |
|----|-----------|------------|--------|--------|
| RF01 | CRUD de Empresas | Alta | 2 | ✅ Concluído |
| RF02 | CRUD de Usuários com perfis (ADMIN/CORRETOR) | Alta | 2 | ✅ Concluído |
| RF03 | Autenticação JWT | Alta | 2 | ✅ Concluído |
| RF04 | CRUD de Proprietários | Alta | 3 | ⬜ Pendente |
| RF05 | CRUD de Clientes | Alta | 4 | ⬜ Pendente |
| RF06 | CRUD de Imóveis com upload de fotos | Alta | 5 | ⬜ Pendente |
| RF07 | Filtros e busca de imóveis | Alta | 5 | ⬜ Pendente |
| RF08 | Contrato de Venda (Financiamento) | Alta | 6 | ⬜ Pendente |
| RF09 | Geração de parcelas de venda | Alta | 6 | ⬜ Pendente |
| RF10 | Reajuste anual pelo IGP-M | Alta | 6 | ⬜ Pendente |
| RF11 | Contrato de Aluguel | Alta | 7 | ⬜ Pendente |
| RF12 | Geração de prestações de aluguel | Alta | 7 | ⬜ Pendente |
| RF13 | Marcar pagamento de parcelas/prestações | Alta | 8 | ⬜ Pendente |
| RF14 | Cálculo automático de juros e multa por atraso | Alta | 8 | ⬜ Pendente |
| RF15 | Geração de carnê PDF | Média | 8 | ⬜ Pendente |
| RF16 | Configuração de email por empresa (SMTP) | Alta | 9 | ⬜ Pendente |
| RF17 | Envio de cobranças por email | Alta | 9 | ⬜ Pendente |
| RF18 | Dashboard com indicadores | Alta | 10 | ⬜ Pendente |
| RF19 | Relatório de contas a receber | Alta | 10 | ⬜ Pendente |
| RF20 | Relatório de inadimplentes | Alta | 10 | ⬜ Pendente |
| RF21 | Histórico do cliente | Média | 10 | ⬜ Pendente |
| RF22 | Exportar relatórios para Excel | Média | 10 | ⬜ Pendente |

---
