# SmartPark

Projet AWD 2026-2027 : plateforme de gestion intelligente de parkings
en architecture microservices.
1. Présentation du projet

SmartPark est une application intelligente de gestion et de réservation de places de parking.

Le projet vise à faciliter la recherche et la réservation des places disponibles, à améliorer la gestion des parkings et à simplifier le processus de paiement.

La solution repose sur une architecture en microservices permettant de séparer les responsabilités métier, d'assurer l'indépendance des données et de faciliter l'évolution et la maintenance du système.

## Équipe
| Membre | Microservice |
|---|---|
| Neirouz Ghabri | user-service |
| Rayen Hafian | parking-info-service |
| Nour Khemissi | reservation-service |
| Mohamed Aziz Khaldi | payment-service |
| (à décider) | ai-service (technologie avancée) |

## Sujet
(Problème traité en 3 à 5 lignes.)

## Acteurs
(Liste des acteurs.)

## Architecture
![Architecture](Documentation/Diagrams/Architecture.png)

## Microservices
| Service | Responsabilité | Base |
|---|---|---|
| user-service | Comptes, profils, véhicules | user_db (MySQL) |
| parking-info-service | Parkings, zones, places | parking_db (MySQL) |
| reservation-service | Réservations et sessions | reservation_db (MySQL) |
| payment-service | Tarifs, paiements, factures | payment_db (MySQL) |
| ai-service | (à compléter) | (à compléter) |

## Stack technique
Symfony, MySQL, Docker, ...

## documentation et présentation

Les livrables du projet sont organisés comme suit :

Documentation/Presentation/ : support de présentation du projet.

Documentation/Diagrams/ : diagramme global de l'architecture technique.

README.md : présentation générale, architecture et instructions de lancement.

## Lancement
(À compléter.)

## Gestion de projet
(Lien vers le tableau GitHub Projects ou Trello.)
