-- phpMyAdmin SQL Dump
-- version 5.3.0-dev+20230112.05c5217976
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : ven. 13 jan. 2023 à 02:11
-- Version du serveur : 10.4.24-MariaDB
-- Version de PHP : 8.1.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `chatjava`
--

-- --------------------------------------------------------

--
-- Structure de la table `amis`
--

CREATE TABLE `amis` (
  `id_ami` int(11) NOT NULL,
  `nom` varchar(20) NOT NULL,
  `id_user` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `amis`
--

INSERT INTO `amis` (`id_ami`, `nom`, `id_user`) VALUES
(1, 'ahmed', 1),
(2, 'hiiii', 1),
(3, 'omar', 1),
(4, 'abdo', 2),
(5, 'hiiii', 2),
(6, 'abdo', 4),
(7, 'ahmed', 4),
(8, 'omar', 4),
(9, 'abdo', 6),
(10, 'hiiii', 6);

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE `users` (
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `id_user` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`username`, `password`, `id_user`) VALUES
('abdo', '123', 1),
('ahmed', '123', 2),
('hiiii', '123', 4),
('omar', '123', 6),
('hmida', '123', 7),
('hmida', '123', 8),
('hmida', '123', 9),
('hmida', '123', 10),
('hmida', '123', 11),
('hmida', '123', 12),
('hmida', '123', 13),
('hmida', '123', 14),
('hmida', '123', 15),
('hmida', '123', 16),
('hmida', '123', 17),
('hmida', '123', 18),
('hmida', '123', 19),
('hmida', '123', 20),
('hmida', '123', 21),
('hmida', '123', 22),
('hmida', '123', 23),
('hmida', '123', 24),
('hmida', '123', 25),
('hmida', '123', 26),
('hmida', '123', 27);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `amis`
--
ALTER TABLE `amis`
  ADD PRIMARY KEY (`id_ami`);

--
-- Index pour la table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id_user`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `amis`
--
ALTER TABLE `amis`
  MODIFY `id_ami` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT pour la table `users`
--
ALTER TABLE `users`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
