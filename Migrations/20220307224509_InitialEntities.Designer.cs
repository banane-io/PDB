﻿// <auto-generated />
using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Migrations;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;
using Npgsql.EntityFrameworkCore.PostgreSQL.Metadata;
using PDB;

#nullable disable

namespace PDB.Migrations
{
    [DbContext(typeof(ApplicationContext))]
    [Migration("20220307224509_InitialEntities")]
    partial class InitialEntities
    {
        protected override void BuildTargetModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasAnnotation("ProductVersion", "6.0.2")
                .HasAnnotation("Relational:MaxIdentifierLength", 63);

            NpgsqlModelBuilderExtensions.UseIdentityByDefaultColumns(modelBuilder);

            modelBuilder.Entity("PDB.Models.Base", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("uuid");

                    b.Property<Guid>("LocationId")
                        .HasColumnType("uuid");

                    b.Property<Guid>("OwnerId")
                        .HasColumnType("uuid");

                    b.Property<int>("Stone")
                        .HasColumnType("integer");

                    b.Property<int>("Wood")
                        .HasColumnType("integer");

                    b.HasKey("Id");

                    b.HasIndex("LocationId");

                    b.HasIndex("OwnerId");

                    b.ToTable("Bases");
                });

            modelBuilder.Entity("PDB.Models.Hero", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("uuid");

                    b.Property<int>("Agility")
                        .HasColumnType("integer");

                    b.Property<Guid>("CurrentZoneId")
                        .HasColumnType("uuid");

                    b.Property<int>("Hp")
                        .HasColumnType("integer");

                    b.Property<int>("Intelligence")
                        .HasColumnType("integer");

                    b.Property<int>("Mana")
                        .HasColumnType("integer");

                    b.Property<int>("Stone")
                        .HasColumnType("integer");

                    b.Property<int>("Strength")
                        .HasColumnType("integer");

                    b.Property<int>("Wood")
                        .HasColumnType("integer");

                    b.HasKey("Id");

                    b.HasIndex("CurrentZoneId");

                    b.ToTable("Heroes");
                });

            modelBuilder.Entity("PDB.Models.MapPoint", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("uuid");

                    b.Property<int>("Terrain")
                        .HasColumnType("integer");

                    b.Property<int>("X")
                        .HasColumnType("integer");

                    b.Property<int>("Y")
                        .HasColumnType("integer");

                    b.Property<string>("Zone")
                        .IsRequired()
                        .HasColumnType("text");

                    b.HasKey("Id");

                    b.ToTable("MapPoints");
                });

            modelBuilder.Entity("PDB.Models.Base", b =>
                {
                    b.HasOne("PDB.Models.MapPoint", "Location")
                        .WithMany()
                        .HasForeignKey("LocationId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.HasOne("PDB.Models.Hero", "Owner")
                        .WithMany()
                        .HasForeignKey("OwnerId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.Navigation("Location");

                    b.Navigation("Owner");
                });

            modelBuilder.Entity("PDB.Models.Hero", b =>
                {
                    b.HasOne("PDB.Models.MapPoint", "CurrentZone")
                        .WithMany()
                        .HasForeignKey("CurrentZoneId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.Navigation("CurrentZone");
                });
#pragma warning restore 612, 618
        }
    }
}
